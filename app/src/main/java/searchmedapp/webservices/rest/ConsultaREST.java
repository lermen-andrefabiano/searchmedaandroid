package searchmedapp.webservices.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import android.util.Log;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.ConsultaDTO;
import searchmedapp.webservices.dto.InformacaoClassificarDTO;
import searchmedapp.webservices.dto.InformacaoNotificarDTO;

/**
 * Created by Andre on 02/07/2015.
 */
public class ConsultaREST extends AbstractREST{

    private static final String PATH = "consulta/";

    public boolean abrir(Long usuarioId, Long medicoId, Long especialidadeId, Long horarioId) throws Exception {
        final String PATH_ABRIR = "abrir?usuarioId="+usuarioId+"&medicoId="+medicoId+"&especialidadeId="+especialidadeId+"&horarioId="+horarioId;

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

    public List<ConsultaDTO> consultasAbertas(Long medicoId) throws Exception {
        final String PATH_ABERTOS = "consultasAbertas?medicoId=";
        Log.i("URL_WS", URL_WS + PATH_ABERTOS + medicoId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ABERTOS + medicoId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ConsultaDTO> lst = new ArrayList<ConsultaDTO>();
            JsonParser parser = new JsonParser();
            JsonObject obj = null;
            JsonArray array = null;

            try{
                obj = parser.parse(resposta[1]).getAsJsonObject();
                array = obj.getAsJsonArray("consulta");

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), ConsultaDTO.class));
                }

            }catch(ClassCastException c){
                lst.add(gson.fromJson(obj.getAsJsonObject("consulta"), ConsultaDTO.class));
            }catch (Exception e){
                e.printStackTrace();
            }



            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public void agendar(Long consultaId, String agendamento, String observacao) throws Exception {
        final String PATH_NOTIFICAR = "agendar?consultaId=" +consultaId;
        Log.i("URL_WS", URL_WS + PATH + PATH_NOTIFICAR);

        InformacaoNotificarDTO info = new InformacaoNotificarDTO();
        //info.setAgendamento(agendamento);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_NOTIFICAR, infoJSON);

       if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0]);
       }

    }

    public List<ConsultaDTO> listarClassificacoes(Long usuarioId) throws Exception {
        final String PATH_CLASSIFICACAO = "classificacao?usuarioId=";
        Log.i("URL_WS", URL_WS + PATH_CLASSIFICACAO + usuarioId);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_CLASSIFICACAO + usuarioId);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ConsultaDTO> lst = new ArrayList<ConsultaDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try{
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), ConsultaDTO.class));
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

    public boolean classificar(Long nota, String recomendacao, Long consultaId) throws Exception {
        final String PATH_CLASSIFICAR = "classificar?consultaId="+consultaId;
        Log.i("URL_WS", URL_WS + PATH + PATH_CLASSIFICAR);

        InformacaoClassificarDTO info = new InformacaoClassificarDTO();
        info.setRecomendacao(recomendacao);
        info.setNota(nota);

        Gson gson = new Gson();
        String infoJSON = gson.toJson(info);

        String[] resposta = new WebServiceClient().post(URL_WS + PATH + PATH_CLASSIFICAR, infoJSON);

        if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0]);
        }
        return Boolean.valueOf(resposta[1]);
    }

}
