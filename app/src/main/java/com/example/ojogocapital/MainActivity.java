package com.example.ojogocapital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String[][] estados = {
            {"Acre","Rio Branco"},
            {"Alagoas","Maceió"},
            {"Amazonas","Manaus"},
            {"Bahia","Salvador"},
            {"Ceará","Fortaleza"},
            {"Espírito Santo","Vitória"},
            {"Goiás", "Goiânia"},
            {"Maranhão","São Luís"},
            {"Mato Grosso", "Cuiabá"},
            {"Mato Grosso do Sul", "Campo Grande"},
            {"Paraíba","João Pessoa"},
            {"Paraná", "Curitiba"},
            {"Pernambuco","Recife"},
            {"Piauí","Teresina"},
            {"Rio Grande do Norte","Natal"}
    };

    EditText inputCapital;
    TextView ViewEstado, ViewPont, ViewFase, ViewResult;
    Button ButtonJn, ButtonV, ButtonProx;

    ArrayList<Integer> Estados = new ArrayList<>();
    int Pontos = 0, Numeroderodada = 1, NumerodoEstado;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inicializar();

        NumerodoEstado = rand.nextInt(15);
        Estados.add(NumerodoEstado);
        ViewEstado.setText( "Qual é a Capital do estado de " + estados[NumerodoEstado][0] + "?");
        ViewPont.setText("Score:" + String.valueOf(Pontos));
        ButtonProx.setEnabled(false);
    }

    public void Inicializar(){
        inputCapital = findViewById(R.id.inputCap);

        ViewEstado = findViewById(R.id.textEstado);
        ViewPont = findViewById(R.id.textViewPontuacao);
        ViewFase = findViewById(R.id.textRodada);
        ViewResult = findViewById(R.id.textResultado);

        ButtonJn = findViewById(R.id.btnJN);
        ButtonV = findViewById(R.id.btnValida);
        ButtonProx = findViewById(R.id.btnProx);
    }

    public void Verificar(View view){
        if(inputCapital.length() == 0){
            Toast.makeText(this, "Digite o nome da capital.",Toast.LENGTH_SHORT).show();
            return ;
        }
        ButtonV.setEnabled(false);
        String capitalBD = deAccent(estados[NumerodoEstado][1].toUpperCase());
        String capitalUser = deAccent(inputCapital.getText().toString().toUpperCase());
        if(capitalBD.equals(capitalUser)){
            Pontos += 10;
            ViewPont.setText("Score: " + String.valueOf(Pontos));
            ViewResult.setText("Certa resposta!");
        }else{
            ViewResult.setText("Você errou, a resposta correta é: " + estados[NumerodoEstado][1]);
        }
        if(Numeroderodada == 5){
            ViewEstado.setText("Fim de Jogo");
            ButtonProx.setVisibility(view.INVISIBLE);
            ButtonV.setVisibility(view.INVISIBLE);
            inputCapital.setVisibility(view.INVISIBLE);
            ButtonJn.setVisibility(view.VISIBLE);
        }else{
            ButtonJn.setVisibility(view.INVISIBLE);
            ButtonProx.setEnabled(true);
        }
    }

    public void proximaPergunta(View view){
        inputCapital.setText("");
        ViewResult.setText("");
        ButtonV.setVisibility(view.VISIBLE);
        ButtonV.setEnabled(true);
        ButtonProx.setVisibility(view.VISIBLE);
        ButtonProx.setEnabled(false);
        Numeroderodada ++;
        ViewFase.setText("Rodada "+ Numeroderodada +" de 5");
        sortearCap();
        ViewEstado.setText( "Qual é a Capital do estado de " + estados[NumerodoEstado][0] + "?");
    }

    public void sortearCap(){
        NumerodoEstado = rand.nextInt(15);
        while(Estados.contains(NumerodoEstado)){
            NumerodoEstado = rand.nextInt(15);
        }
        Estados.add(NumerodoEstado);
    }

    public void jogarNovamente(View view){
        Numeroderodada = 1;
        Pontos = 0;
        Estados = new ArrayList<>();
        sortearCap();
        ButtonV.setVisibility(view.VISIBLE);
        ButtonProx.setVisibility(view.VISIBLE);
        ButtonJn.setVisibility(view.INVISIBLE);
        inputCapital.setVisibility(view.VISIBLE);
        if (Numeroderodada == 1){
            ViewPont.setText( "Score: " + String.valueOf(Pontos));
            ViewEstado.setText( "Qual é a Capital do estado de " + estados[NumerodoEstado][0] + "?");
            ViewFase.setText("Rodada "+ Numeroderodada +" de 5");
            ButtonV.setEnabled(true);
            ButtonProx.setEnabled(false);
            inputCapital.setText("");
            ViewResult.setText("");
        }
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}