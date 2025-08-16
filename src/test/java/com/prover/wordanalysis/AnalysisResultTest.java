package com.prover.wordanalysis;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import static org.junit.Assert.*;


public class AnalysisResultTest {

    @Test
    public void testConstructorAndGetters() {
        Map<String, Integer> occurrences = new HashMap<>();
        occurrences.put("teste", 2);
        occurrences.put("completo", 1);
        String json = new Gson().toJson(occurrences);
        AnalysisResult result = new AnalysisResult("teste teste completo", 2, json);

        assertEquals("teste teste completo", result.getPhrase());
        assertEquals(2, result.getDistinctWords());
        assertEquals(json, result.getOccurrencesJson());
    }

    @Test
    public void testGetOccurrencesMap() {
        Map<String, Integer> occurrences = new HashMap<>();
        occurrences.put("teste", 2);
        occurrences.put("completo", 1);
        String json = new Gson().toJson(occurrences);
        AnalysisResult result = new AnalysisResult("teste teste completo", 2, json);

        Map<String, Integer> map = result.getOccurrencesMap();
        assertEquals(2, map.size());
        assertEquals(Integer.valueOf(2), map.get("teste"));
        assertEquals(Integer.valueOf(1), map.get("completo"));
    }

    @Test
    public void testEmptyOccurrences() {
        AnalysisResult result = new AnalysisResult("", 0, "{}");
        assertTrue(result.getOccurrencesMap().isEmpty());
    }

    @Test
    public void testNullOccurrencesJson() {
        AnalysisResult result = new AnalysisResult("frase sem ocorrencias", 0, null);
        Map<String, Integer> map = result.getOccurrencesMap();
        assertTrue(map.isEmpty());
    }

    @Test
    public void testInvalidJsonOccurrences() {
        AnalysisResult result = new AnalysisResult("frase com json invalido", 0, "{invalid json}");
        Map<String, Integer> map = result.getOccurrencesMap();
        assertTrue(map.isEmpty());
    }
}