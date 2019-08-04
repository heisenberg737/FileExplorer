package filelist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import heisenberg737.fileexplorer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsideDirectoryFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    File currentDir;
    FilesListAdapter adapter;
    ArrayList<FilesListClass> dir=new ArrayList<>();
    ArrayList<FilesListClass> fls=new ArrayList<>();


    public InsideDirectoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inside_directory, container, false);

        Bundle bundle=getArguments();
        if(bundle!=null)
           currentDir=new File((String) bundle.get("CurrentDirectory"));

        recyclerView=view.findViewById(R.id.inside_directory_list);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fill(currentDir);


        return view;
    }

    public void fill(File file)
    {
        File[] dirs=file.listFiles();

        try {
            for(File ff:dirs) {
                if (ff.isDirectory()) {
                    File[] fbuf = ff.listFiles();
                    int buf = 0;
                    if (fbuf != null) {
                        buf = fbuf.length;
                    } else buf = 0;
                    String num_item = String.valueOf(buf);
                    if (buf == 0)
                        num_item = num_item + " item";
                    else num_item = num_item + "items";

                    dir.add(new FilesListClass(ff.getName(), num_item));
                } else {
                    fls.add(new FilesListClass(ff.getName(),ff.length()+" Bytes"));
                }
            }



            } catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        dir.addAll(fls);
        adapter=new FilesListAdapter(getContext(),dir,dirs);
        recyclerView.setAdapter(adapter);
    }

}
