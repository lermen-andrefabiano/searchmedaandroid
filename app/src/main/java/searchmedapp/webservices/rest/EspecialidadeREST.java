package searchmedapp.webservices.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import android.util.Log;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.EspecialidadeDTO;
import searchmedapp.webservices.dto.MedicoDTO;


/**
 * Created by Andre on 02/07/2015.
 */
public class EspecialidadeREST extends AbstractREST{

    private static final String PATH = "especialidade/";

    public List<MedicoDTO> getMedicoEspecialidades(String convenio, Long especialidadeId) throws Exception {
        String PATH_ESP = "getMedicoEspecialidade?convenio="+convenio+"&especialidadeId="+especialidadeId;
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<MedicoDTO> lst = new ArrayList<MedicoDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try{
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), MedicoDTO.class));
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

    public List<EspecialidadeDTO> getEspecialidades() throws Exception {
        String PATH_ESP = "getEspecialidades";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<EspecialidadeDTO> lst = new ArrayList<EspecialidadeDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try{
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), EspecialidadeDTO.class));
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

    public void excluir(Long medicoEspecialidadeId) throws Exception {
        String PATH_ESP = "excluir?medicoEspecialidadeId=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP + medicoEspecialidadeId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + medicoEspecialidadeId);

        if (resposta[0].equals("200")) {

        } else {
            throw new Exception(resposta[1]);
        }
    }
}
