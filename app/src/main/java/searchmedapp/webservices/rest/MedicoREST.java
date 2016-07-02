package searchmedapp.webservices.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.MedicoDTO;
import searchmedapp.webservices.dto.UsuarioDTO;

/**
 * Created by Andre on 02/07/2015.
 */
public class MedicoREST extends AbstractREST {

    private static final String PATH = "medico/";

    public boolean inclurEspecialidade(Long usuarioId, Long especialidadeId) throws Exception {
        final String PATH_ABRIR = "incluirE?usuarioId="+usuarioId+"&especialidadeId="+especialidadeId;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ABRIR);

        if(resposta[0].equals("400")){
            return false;
        }else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

    public boolean excluirEspecialidade(Long usuarioId, Long especialidadeId) throws Exception {
        final String PATH_ABRIR = "excluirE?usuarioId="+usuarioId+"&especialidadeId="+especialidadeId;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ABRIR);

        if(resposta[0].equals("400")){
            return false;
        }else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

}