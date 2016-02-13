package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends ActionBarActivity {

    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        this.helper = new FormularioHelper(this);

        Intent intent = this.getIntent();

        Aluno aluno = (Aluno)intent.getSerializableExtra("aluno");
        if (aluno != null) {
            this.helper.preencheFormulario(aluno);
        }

        Button btnFoto = (Button)findViewById(R.id.formulario_botao_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FormularioActivity.this.caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                File arquivo = new File(caminhoFoto);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivo));

                startActivityForResult(intent, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            ImageView foto = (ImageView) findViewById(R.id.formulario_foto);
            Bitmap bm = BitmapFactory.decodeFile(this.caminhoFoto);

            Bitmap bmReduzido = Bitmap.createScaledBitmap(bm,(int)(bm.getWidth()*0.3), (int)(bm.getHeight()*0.3), true);

            foto.setTag(this.caminhoFoto);

            foto.setImageBitmap(bmReduzido);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_ok:
                Aluno aluno = this.helper.pegaAlunoDoFormulario();
                AlunoDAO dao = new AlunoDAO(this);

                if (aluno.getId() == null) {
                    dao.insere(aluno);
                } else {
                    dao.altera(aluno);
                }

                finish();
        }
        return false;
    }

}
