package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.model.Company;
import com.wise.roommaster.service.CheckDomainService;
import com.wise.roommaster.service.SignupService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class SignupActivity extends AppCompatActivity {
    List<Company> companyList = new ArrayList<>();
    List<String> companyNameList= new ArrayList<>();
    String resulte;
    int selectCompanyId = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }


        setContentView(R.layout.activity_start_signup);
        final EditText nameEdt = findViewById(R.id.signup_username_field);
        final EditText emailEdt = findViewById(R.id.signup_email_field);
        final EditText passwordEdt = findViewById(R.id.signup_password_field1);
        final Spinner companySpn = findViewById(R.id.companySpinner);
        companySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SignupActivity.this, ("ID DA EMRPESA: " + companyList.get(position).getId()), Toast.LENGTH_SHORT).show();
                    selectCompanyId=companyList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        emailEdt.setOnFocusChangeListener((new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    String emailAfterTextChange = emailEdt.getText().toString();
                    if(!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()){
                        emailEdt.setError("Email inválido.");
                    }
                    if(emailAfterTextChange.contains("@") && chkPassword(emailEdt.getText().toString())){
                        String[] emailFull = emailAfterTextChange.split("@");
                        if(emailFull.length > 1){
                            String domain = emailFull[1];

                            if(domain.contains(".")){
                                System.out.println("dominio: " + domain);
                                try{
                                    resulte = new CheckDomainService(domain).execute().get();
                                    //Integer[] companyId;
                                    JSONArray resultJson = new JSONArray(resulte);

                                    //String[] companyName;
                                    if(resultJson.length()>0){

                                        //companyId = new Integer[resultJson.length()];
                                        //companyName = new String[resultJson.length()];
                                        companyList.clear();
                                        companyNameList.clear();
                                        for(int i = 0;i < resultJson.length(); i++){
                                            JSONObject obj = resultJson.getJSONObject(i);
                                            if(obj.has("id")&&obj.has("nome")&&obj.has("tipoOrganizacao")){
                                                int id = obj.getInt("id");
                                                String name = obj.getString("nome");
                                                String type = obj.getString("tipoOrganizacao");
                                                Company company = new Company();
                                                //System.out.println(companyId[i]);
                                                company.setId(id);
                                                company.setName(name);
                                                company.setCompanyType(type);
                                                
                                                companyList.add(company);

                                                companyNameList.add(company.getName()+" - "+company.getCompanyType());
                                            }
                                            //System.out.println("id da empresa:" + companyId[i]);

                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignupActivity.this, android.R.layout.simple_spinner_item, companyNameList);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        companySpn.setAdapter(adapter);
                                        companySpn.setVisibility(View.VISIBLE);

                                    }

                                    System.out.println(resulte);

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                if(resulte.equals("[]")){
                                    emailEdt.setError("Domínio não cadastrado.");
                                }

                                //do stuff -> check domain
                                //         -> check companyId

                            }else{
                                Toast.makeText(SignupActivity.this, "O @ está incompleto!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }));
        passwordEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!chkPassword(passwordEdt.getText().toString())){
                        passwordEdt.setError("A senha deve ter ao menos 6 caracteres.");
                    }
                }
            }
        });


        Button SignupBtn = findViewById(R.id.signup_signup_button);

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, password, email;
                name = nameEdt.getText().toString();
                email = emailEdt.getText().toString();
                password = passwordEdt.getText().toString();

                try {
                    String result = new SignupService(name,email,password,selectCompanyId).execute().get();
                    //System.out.println(new LoginService(email,password).execute().get());
                    System.out.println("Resultado" + result);
                    if(name.equals("")||email.equals("")||password.equals(""))
                        Toast.makeText(SignupActivity.this, "Os campos não foram preenchidos corretamente.", Toast.LENGTH_SHORT).show();
                    else if(result.equals("Usuário criado com sucesso")){
                        Toast.makeText(SignupActivity.this, "Cadastro efetuado!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));

                        //MainActivity.isLogged = true;
                    }else{
                        Toast.makeText(SignupActivity.this, result, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean chkPassword(String password){
        System.out.println("Tamanho certo: " + (password.length()>5));
        //boolean valido;

        //System.out.println("Caracteres certos: " + (matcher.matches()));
        return (password.length()>5);

    }

}
