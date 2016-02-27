package br.com.caelum.cadastro;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.cadastro.task.EnviaAlunosTask;


public class ListaAlunosActivity extends ActionBarActivity {

    public static final String ALUNO_SELECIONADO = "alunoSelecionado";

    private ListView listaAlunos;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        this.carregaLista();
        registerForContextMenu(this.listaAlunos);

        this.listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                Aluno aluno = (Aluno)parent.getItemAtPosition(position);
                edicao.putExtra("aluno", aluno);

                startActivity(edicao);
            }
        });

        Button botaoAdiciona = (Button)findViewById(R.id.lista_alunos_floating_button);

        botaoAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(formulario);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        this.carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = ListaAlunosActivity.this.alunos.get(info.position);

        MenuItem delete = menu.add("Deletar");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.delete(alunoSelecionado);
                dao.close();

                carregaLista();

                return false;
            }
        });

        MenuItem ligar = menu.add("Ligar");

        Intent intentLigar = new Intent(Intent.ACTION_DIAL);
        intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        MenuItem sms = menu.add("Enviar SMS");

        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        sms.setIntent(intentSMS);

        MenuItem visitarSite = menu.add("Visitar site");

        Intent intentVisitarSite = new Intent(Intent.ACTION_VIEW);
        intentVisitarSite.setData(Uri.parse("http://" + alunoSelecionado.getSite()));
        visitarSite.setIntent(intentVisitarSite);

        MenuItem mapa = menu.add("Ver no mapa");

        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + alunoSelecionado.getEndereco()));
        mapa.setIntent(intentMapa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mapa:
                Intent mapa = new Intent(this, MostraAlunosActivity.class);
                startActivity(mapa);
                return true;
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                return true;
            case R.id.menu_receber_provas:
                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;
        }
        return false;
    }

    // Private methods

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        this.alunos = dao.getLista();
        dao.close();

        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this.alunos, this);

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        this.listaAlunos.setAdapter(adapter);
    }
}
