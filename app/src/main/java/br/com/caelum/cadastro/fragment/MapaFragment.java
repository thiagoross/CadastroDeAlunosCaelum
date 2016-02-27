package br.com.caelum.cadastro.fragment;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.util.Localizador;

/**
 * Created by thiago on 2/27/16.
 */
public class MapaFragment extends SupportMapFragment {

    @Override
    public void onResume() {
        super.onResume();

        Localizador localizador = new Localizador(getActivity());
        LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");

        this.centralizaNo(local);

        Log.i("MAPA", "Coordenadas da Caelum: " + local);

        AlunoDAO dao = new AlunoDAO(getActivity());
        List<Aluno> alunos = dao.getLista();

        for (Aluno aluno : alunos) {
            LatLng posicaoAluno = localizador.getCoordenada(aluno.getEndereco());

            if (posicaoAluno != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.title(aluno.getNome());
                marcador.position(posicaoAluno);

                getMap().addMarker(marcador);
            }
        }
    }

    private void centralizaNo(LatLng local) {
        GoogleMap mapa = getMap();
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 14));
    }
}
