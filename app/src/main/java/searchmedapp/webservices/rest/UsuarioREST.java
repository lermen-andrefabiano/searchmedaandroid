package searchmedapp.webservices.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import android.util.Log;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.UsuarioDTO;

/**
 * Created by Andre on 02/07/2015.
 */
public class UsuarioREST extends AbstractREST{

    private static final String PATH = "usuario/";

    public UsuarioDTO login(String email, String senha) throws Exception {
        final String PATH_LOGIN = "login";

        Log.i("URL_WS", URL_WS + PATH + PATH_LOGIN);

        UsuarioDTO info = new UsuarioDTO();
        info.setEmail(email);
        info.setSenha(senha);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_LOGIN, infoJSON);

        if (resposta[0].equals("200")) {
            Gson gsonLogin = new Gson();

            JsonParser parser = new JsonParser();
            JsonObject obj = null;

            try {
                obj = parser.parse(resposta[1]).getAsJsonObject();
                info = gsonLogin.fromJson(obj, UsuarioDTO.class);

                return info;
            } catch (Exception e) {
                info = null;
                e.printStackTrace();
            }
        } else {
            info = null;
            throw new Exception(resposta[1]);
        }

        return info;
    }

    public UsuarioDTO criar(Long userId, String nome, String email, String senha, String endereco, String tipo) throws Exception {
        final String PATH_CRIAR = "criar";

        Log.i("URL_WS", URL_WS + PATH + PATH_CRIAR);

        UsuarioDTO info = new UsuarioDTO();
        info.setId(userId);
        info.setNome(nome);
        info.setEmail(email);
        info.setEndereco(endereco);
        info.setSenha(senha);
        info.setTipo(tipo);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_CRIAR, infoJSON);

        if (resposta[0].equals("200")) {
            Gson gsonLogin = new Gson();

            JsonParser parser = new JsonParser();
            JsonObject obj = null;

            try {
                obj = parser.parse(resposta[1]).getAsJsonObject();
                info = gsonLogin.fromJson(obj, UsuarioDTO.class);

                return info;
            } catch (Exception e) {
                info = null;
                e.printStackTrace();
            }
        } else {
            info = null;
            throw new Exception(resposta[1]);
        }

        return info;
    }

}
