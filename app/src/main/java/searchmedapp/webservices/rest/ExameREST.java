package searchmedapp.webservices.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import searchmedapp.webservices.WebServiceClient;
import searchmedapp.webservices.dto.ConsultaExameDTO;
import searchmedapp.webservices.dto.ExameConsultaDTO;
import searchmedapp.webservices.dto.ExameDTO;
import searchmedapp.webservices.dto.InfoSalvarHorarioDTO;
import searchmedapp.webservices.dto.MedicoConvenioDTO;
import searchmedapp.webservices.dto.MedicoEspecialidadeDTO;
import searchmedapp.webservices.dto.MedicoHorarioDTO;

/**
 * Created by Andre on 31/07/2015.
 */
public class ExameREST extends AbstractREST {

    private static final String PATH = "exame/";

    public boolean incluir(Long consultaId, Long exameId) throws Exception {
        final String PATH_ABRIR = "incluir?consultaId=" + consultaId + "&exameId=" + exameId;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ABRIR);

        if (resposta[0].equals("400")) {
            return false;
        } else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

    public boolean excluir(Long consultaId, Long exameId) throws Exception {
        final String PATH_ABRIR = "excluir?consultaId=" + consultaId + "&exameId=" + exameId;

        Log.i("URL_WS", URL_WS + PATH + PATH_ABRIR);

        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ABRIR);

        if (resposta[0].equals("400")) {
            return false;
        } else if (resposta[0].equals("200")) {
            Log.i("resposta[0]", resposta[0] + " valor " + resposta[1]);
            return Boolean.valueOf(resposta[1]);
        }

        return false;
    }

    public List<ExameDTO> getExames() throws Exception {
        String PATH_ESP = "getExames";
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ExameDTO> lst = new ArrayList<ExameDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try {
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), ExameDTO.class));
                }
            } catch (ClassCastException c) {
                c.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public List<ConsultaExameDTO> getExamesConsulta(Long consultaId) throws Exception {
        String PATH_ESP = "getExamesConsulta?consultaId=" + consultaId;
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ConsultaExameDTO> lst = new ArrayList<ConsultaExameDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try {
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), ConsultaExameDTO.class));
                }
            } catch (ClassCastException c) {
                c.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

    public List<ConsultaExameDTO> getExamesUsuario(Long usuarioId) throws Exception {
        String PATH_ESP = "getExamesUsuario?usuarioId=" + usuarioId;
        Log.i("URL_WS", URL_WS + PATH + PATH_ESP);
        String[] resposta = new WebServiceClient().get(URL_WS + PATH + PATH_ESP);

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<ConsultaExameDTO> lst = new ArrayList<ConsultaExameDTO>();
            JsonParser parser = new JsonParser();
            JsonArray array = null;

            try {
                array = parser.parse(resposta[1]).getAsJsonArray();

                for (int i = 0; i < array.size(); i++) {
                    lst.add(gson.fromJson(array.get(i), ConsultaExameDTO.class));
                }
            } catch (ClassCastException c) {
                c.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lst;
        } else {
            throw new Exception(resposta[1]);
        }
    }

}