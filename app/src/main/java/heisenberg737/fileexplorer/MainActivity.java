package heisenberg737.fileexplorer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import filelist.DisplayFilesFragment;

public class MainActivity extends AppCompatActivity {

    EditText fileName,content;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.MainContent,new DisplayFilesFragment(),null).commit();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                View view1= LayoutInflater.from(MainActivity.this).inflate(R.layout.create_file,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getText(R.string.create_file));
                builder.setView(view1);
                builder.setCancelable(false);
                final Dialog dialog=builder.show();

                Button cancel=view1.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                final Button save=view1.findViewById(R.id.save_file);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveFile(v);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.search_icon,menu);

        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        };

        MenuItem search=menu.findItem(R.id.action_search);
        search.setOnActionExpandListener(onActionExpandListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        switch (id)
//        {
//            case R.id.sort_by:
//                return true;
//            case R.id.select_all:
//                return true;
//            case R.id.add_new_folder:
//                return  true;
//        }


        return super.onOptionsItemSelected(item);
    }

    public void saveFile(View view)
    {
        try {

            fileName=view.findViewById(R.id.create_new_file);
            content=view.findViewById(R.id.file_content);

            File file=new File(getFilesDir()+File.separator+fileName.getText().toString());
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(content.getText().toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void loadData()
    {

    }
}
