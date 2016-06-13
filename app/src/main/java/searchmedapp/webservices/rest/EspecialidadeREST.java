package searchmedapp.webservices.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.util.Log;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;


/**
 * Created by Andre on 02/07/2015.
 */
public class EspecialidadeREST extends AbstractREST{

    private static final String PATH = "medicoEspecialidade/";

    public List<MedicoEspecialidadeDTO> getMedicoEspecialidade(String palavra) throws Exception {
        String PATH_ESP = "getMedicoEspecialidade?palavra=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP + palavra);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + palavra);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<MedicoEspecialidadeDTO> lst = new ArrayList<MedicoEspecialidadeDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("medicoEspecialidade");

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), MedicoEspecialidadeDTO.class));
                }
            }catch(ClassCastException c){
                lst.add(gson.fromJson(obj.getAsJsonObject("medicoEspecialidade"), MedicoEspecialidadeDTO.class));
            }catch (Exception e){
                e.printStackTrace();
            }

            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public List<MedicoEspecialidadeDTO> getEspecialidadesMedicas(Long medicoId) throws Exception {
        String PATH_ESP = "getEspecialidadesMedicas?medicoId=";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP +medicoId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP + medicoId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<MedicoEspecialidadeDTO> lst = new ArrayList<MedicoEspecialidadeDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("medicoEspecialidade");

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), MedicoEspecialidadeDTO.class));
                }
            }catch(ClassCastException c){
                lst.add(gson.fromJson(obj.getAsJsonObject("medicoEspecialidade"), MedicoEspecialidadeDTO.class));
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
