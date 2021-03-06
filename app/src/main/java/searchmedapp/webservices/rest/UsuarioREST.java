package searchmedapp.webservices.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import android.util.Log;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.MedicoDTO;
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

    public boolean trocarSenha(Long usuarioId, String senha, String novaSenha) throws Exception {
        final String PATH_LOGIN = "trocarSenha?usuarioId=" + usuarioId + "&senha=" + senha + "&novaSenha=" + novaSenha;

        Log.i("URL_WS", URL_WS + PATH + PATH_LOGIN);

        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_LOGIN);

        if (resposta[0].equals("400")) {
            return false;
        } else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

    public UsuarioDTO criar(Long userId, String nome, String email, String senha,
                            String endereco, String tipo, String crm,
                            Double latitude, Double longitude) throws Exception {
        final String PATH_CRIAR = "criar";

        Log.i("URL_WS", URL_WS + PATH + PATH_CRIAR);

        UsuarioDTO info = new UsuarioDTO();
        info.setId(userId);
        info.setNome(nome);
        info.setEmail(email);
        info.setEndereco(endereco);
        info.setSenha(senha);
        info.setTipo(tipo);
        info.setLatitude(latitude);
        info.setLongitude(longitude);

        if(crm!=null){
            info.setMedico(new MedicoDTO());
            info.getMedico().setCrm(crm);
        }

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
