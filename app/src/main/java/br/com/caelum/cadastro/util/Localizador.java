package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by thiago on 2/27/16.
 */
public class Localizador {

    private Geocoder geocoder;

    public Localizador(Context context) {
        this.geocoder = new Geocoder(context);
    }

    public LatLng getCoordenada(String endereco) {
        List<Address> lista = null;
        try {
            lista = this.geocoder.getFromLocationName(endereco, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lista.size() > 0) {
            Address address = lista.get(0);

            double lat = address.getLatitude();
            double lng = address.getLongitude();

            return new LatLng(lat, lng);
        }

        return null;
    }
}
