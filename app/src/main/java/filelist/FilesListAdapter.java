package filelist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import heisenberg737.fileexplorer.R;

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.FileListViewHolder> {

    ArrayList<FilesListClass> arrayList;
    Context context;
    File[] files;

    public FilesListAdapter(Context context,ArrayList<FilesListClass> arrayList,File[] files)
    {   this.context=context;
        this.arrayList=arrayList;
        this.files=files;

    }

    @NonNull
    @Override
    public FileListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.files_list_single_row_item,viewGroup,false);
        FileListViewHolder viewHolder=new FileListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileListViewHolder viewHolder, final int i) {

        viewHolder.fileName.setText(arrayList.get(i).getFileName());
        viewHolder.numberOfItems.setText(arrayList.get(i).getNumberOfItems());
        viewHolder.dropDown.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


                menu.add(i,121,0,"Copy");
                menu.add(i,122,1,"Delete");
                menu.add(i,123,2,"Move");
                menu.add(i,124,3,"Rename");
                menu.add(i,125,4,"Share");

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FileListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView fileName,numberOfItems;
        ImageView dropDown;

        public FileListViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName=itemView.findViewById(R.id.file_name);
            numberOfItems=itemView.findViewById(R.id.no_of_items);
            dropDown=itemView.findViewById(R.id.drop_down_menu);
            fileName.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            AppCompatActivity activity=(AppCompatActivity) v.getContext();
            Fragment fragment=new InsideDirectoryFragment();
            Bundle bundle=new Bundle();
            bundle.putString("CurrentDirectory",files[getAdapterPosition()].getAbsolutePath());
            fragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.MainContent,fragment,null).addToBackStack(null).commit();
        }
    }

    public void delete(int position)
    {
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    public void createNewTextFile(int i)
    {
        File file=files[i];
        FilesListClass filesListClass=new FilesListClass(file.getName(),file.length()+" bytes");
        arrayList.add(filesListClass);
        notifyDataSetChanged();

    }

    @Override
    public void onViewRecycled(@NonNull FileListViewHolder holder) {
        holder.dropDown.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }
}
