package com.benxiang.liteormdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.benxiang.liteormdemo.util.DataBaseManager;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DataBaseManager dataBaseManager;
    private RVAdapter rvAdapter;
    private RecyclerView recyclerView;
    private List<Person> list;
    private EditText ev_activity_id, ev_activity_name, ev_activity_age;
    private Button btn_activity_insert, btn_activity_update, btn_activity_query, btn_activity_delete;
    private long result = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        dataBaseManager = DataBaseManager.getInstance(this);
        list = dataBaseManager.queryAll(Person.class);
        recyclerView = findViewById(R.id.rv_activity);
        rvAdapter = new RVAdapter(this, list);
        ev_activity_id = findViewById(R.id.ev_activity_id);
        ev_activity_name = findViewById(R.id.ev_activity_name);
        ev_activity_age = findViewById(R.id.ev_activity_age);
        btn_activity_insert = findViewById(R.id.btn_activity_insert);
        btn_activity_update = findViewById(R.id.btn_activity_update);
        btn_activity_query = findViewById(R.id.btn_activity_query);
        btn_activity_delete = findViewById(R.id.btn_activity_delete);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rvAdapter);

        btn_activity_insert.setOnClickListener(this);
        btn_activity_update.setOnClickListener(this);
        btn_activity_query.setOnClickListener(this);
        btn_activity_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_insert:
                Person person = new Person();
                String str_id = String.valueOf(ev_activity_id.getText());
                String str_name = String.valueOf(ev_activity_name.getText());
                String str_age = String.valueOf(ev_activity_age.getText());
                if (!str_id.equals("")) {
                    person.setId(Integer.valueOf(str_id));
                }
                if (!str_name.equals("") && !str_age.equals("")) {
                    person.setName(str_name);
                    person.setAge(Integer.valueOf(str_age));
                } else if (!str_name.equals("") && str_age.equals("")) {
                    ev_activity_age.setHint("age not null");
                    ev_activity_age.setHintTextColor(getResources().getColor(R.color.colorHint));
                } else if (str_name.equals("") && !str_age.equals("")) {
                    ev_activity_name.setHint("name not null");
                    ev_activity_name.setHintTextColor(getResources().getColor(R.color.colorHint));
                } else {
                    ev_activity_name.setHint("name not null");
                    ev_activity_name.setHintTextColor(getResources().getColor(R.color.colorHint));
                    ev_activity_age.setHint("age not null");
                    ev_activity_age.setHintTextColor(getResources().getColor(R.color.colorHint));
                }
                result = dataBaseManager.insert(person);
                if (result > 0) {
                    Toast.makeText(this, "insert succeed", Toast.LENGTH_SHORT).show();
                    list.add(person);
                    rvAdapter.notifyDataSetChanged();//刷新列表
                    recyclerView.scrollToPosition(list.size() - 1);//定位到最后插入行
                    ev_activity_name.setText("");
                    ev_activity_id.setText("");
                    ev_activity_age.setText("");
                } else {
                    Toast.makeText(this, "insert unsuccessful", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_activity_update:
                if (result > 0) {
                    Person person1 = dataBaseManager.query(result, Person.class);
                    person1.setName("liyuanbiao");
                    int resultInt = dataBaseManager.update(person1);
                    if (resultInt > 0) {
                        Toast.makeText(this, "update succeed", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(dataBaseManager.queryAll(Person.class));
                        rvAdapter.notifyDataSetChanged();//刷新列表
                        recyclerView.scrollToPosition((int) person1.getId());//定位到最后插入行
                    }
                }
                break;
            case R.id.btn_activity_query:
                list.clear();
                list.addAll(dataBaseManager.queryAll(Person.class));
                rvAdapter.notifyDataSetChanged();//刷新列表
                break;
            case R.id.btn_activity_delete:
                if(result > 0){
                    Person person2 = dataBaseManager.query(result, Person.class);
                    dataBaseManager.delete(person2);
                    list.clear();
                    list.addAll(dataBaseManager.queryAll(Person.class));
                    rvAdapter.notifyDataSetChanged();//刷新列表
                }
                break;
            default:
                break;
        }
    }
}
