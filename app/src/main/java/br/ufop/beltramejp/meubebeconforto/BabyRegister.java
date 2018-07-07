package br.ufop.beltramejp.meubebeconforto;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.app.Dialog;
import android.widget.TextView;

public class BabyRegister extends AppCompatActivity {

    private EditText name, dateBirth;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMale, radioButtonFemale;
    private BabyData babyData;
    private Dialog dialog;
    private TextView dialogTextBabyRegister;

    private boolean dialogChoice, dialogOp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        babyData = babyData.getInstance(this);

        name = findViewById(R.id.name);
        dateBirth = findViewById(R.id.dateBirth);

        radioGroup = findViewById(R.id.radioGroup);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onClickCadastrar(View view) {
        babyData.setBabyCadastred(true);
        babyData.setName(this, name.getText().toString());
        babyData.setDateBirth(this, dateBirth.getText().toString());
        if(radioButtonMale.isChecked()){
            babyData.setSex(this, 'M');
        }else if(radioButtonFemale.isChecked()){
            babyData.setSex(this, 'F');
        }
        finish();
    }

    public void onClickCancelar(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
