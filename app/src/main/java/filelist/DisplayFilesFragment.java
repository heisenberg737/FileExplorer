package filelist;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import heisenberg737.fileexplorer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFilesFragment extends Fragment {


    public static final String TAG = "DisplayFiles";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FilesListAdapter adapter;
    File currentDir;
    ArrayList<FilesListClass> dir=new ArrayList<>();
    ArrayList<FilesListClass>fls =new ArrayList<>();

    public DisplayFilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_display_files, container, false);

        currentDir=new File(Environment.getExternalStorageDirectory().getPath());

        recyclerView=view.findViewById(R.id.files_list);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fill(currentDir);

        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case 121:

            case 122:
                 adapter.delete(item.getGroupId());
                 displayMessage("Item Deleted");

            default:
                return super.onContextItemSelected(item);

        }

    }

    public void displayMessage(String message)
    {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void fill(File file)
    {   dir.clear();
        File[] dirs=file.listFiles();
        try {
            for(File ff:dirs)
            {
                if(ff.isDirectory())
                {
                    File[] fbuf=ff.listFiles();
                    int buf=0;
                    if(fbuf!=null)
                    {
                        buf=fbuf.length;
                    }
                    else buf=0;
                    String num_item=String.valueOf(buf);
                    if(buf==0)
                        num_item=num_item+" item";
                    else num_item=num_item+"items";

                    dir.add(new FilesListClass(ff.getName(),num_item));
                }
                else {
                    fls.add(new FilesListClass(ff.getName(),ff.length()+" Bytes"));
                }

            }
        } catch (Exception e)
        {
            Log.v("DisplayFile", "error :" + e.toString());
        }
        dir.addAll(fls);
        if(file.getName().equalsIgnoreCase(Environment.getExternalStorageState()))
              dir.add(0,new FilesListClass("Parent Directory",file.length()+" items"));
        adapter=new FilesListAdapter(getContext(),dir,dirs);
        recyclerView.setAdapter(adapter);


    }

}
