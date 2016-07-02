package searchmedapp.webservices.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.MedicoConvenioDTO;
import searchmedapp.webservices.dto.MedicoDTO;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;
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

    public List<MedicoEspecialidadeDTO> getEspecialidadesMedicas(Long medicoId) throws Exception {
        String PATH_ESP = "getEspecialidadesMedicas?medicoId=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP +medicoId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + medicoId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<MedicoEspecialidadeDTO> lst = new ArrayList<MedicoEspecialidadeDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try{
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), MedicoEspecialidadeDTO.class));
                }
            }catch(ClassCastException c){
                c.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public boolean inclurConvenio(Long medicoId, String convenio) throws Exception {
        final String PATH_ABRIR = "incluirC?medicoId="+medicoId;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_ABRIR, convenio);

        if(resposta[0].equals("400")){
            return false;
        }else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

    public boolean excluirConvenio(Long medicoId, String convenio) throws Exception {
        final String PATH_ABRIR = "excluirC?medicoId="+medicoId;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_ABRIR, convenio);

        if(resposta[0].equals("400")){
            return false;
        }else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

    public List<MedicoConvenioDTO> getMedicoConvenio(Long medicoId) throws Exception {
        String PATH_ESP = "getMedicoConvenio?medicoId=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP +medicoId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + medicoId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<MedicoConvenioDTO> lst = new ArrayList<MedicoConvenioDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try{
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), MedicoConvenioDTO.class));
                }
            }catch(ClassCastException c){
                c.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

}