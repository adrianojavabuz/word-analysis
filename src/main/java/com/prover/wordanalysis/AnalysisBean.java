package com.prover.wordanalysis;

import com.prover.wordanalysis.dao.AnalysisResultDAO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class AnalysisBean implements Serializable {

    private static final Logger logger = Logger.getLogger(AnalysisBean.class.getName());

    private String phrase;
    private Map<String, Integer> occurrences;
    private Integer distinct;
    private Map<String, AnalysisResult> history;

    @EJB
    AnalysisResultDAO analysisResultDAO;

    @PostConstruct
    public void init() {
        occurrences = new HashMap<>();
        history = analysisResultDAO.findAll(); // Carrega o histórico do banco
        logger.info("AnalysisBean inicializado. Occurrences: " + occurrences + ", History: " + history);
    }

    public void analyze() {
        logger.info("Iniciando análise para frase: " + phrase);
        if (phrase == null || phrase.isEmpty()) {
            occurrences.clear();
            distinct = 0;
            logger.info("Frase vazia ou nula, limpando dados. Occurrences: " + occurrences + ", Distinct: " + distinct);
            return;
        }
        occurrences = analyzePhrase(phrase);
        distinct = occurrences.size();
        logger.info("Ocorrências calculadas: " + occurrences + ", Distintas: " + distinct);
        persistResult();
        logger.info("Histórico após persistência: " + history + ", Occurrences: " + occurrences);
    }

    private Map<String, Integer> analyzePhrase(String phrase) {
        Map<String, Integer> map = new HashMap<>();
        String[] words = phrase.split("\\s+");
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-záéíóúçãõ]", "");
            if (!word.isEmpty()) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }
        return map;
    }

    private void persistResult() {
        if (analysisResultDAO == null) {
            logger.warning("AnalysisResultDAO não injetado!");
            return;
        }
        AnalysisResult result = new AnalysisResult(phrase, distinct, new com.google.gson.Gson().toJson(occurrences));
        analysisResultDAO.persist(result);
    }

    // Getters e Setters
    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
        logger.info("Setter chamado, nova frase: " + this.phrase);
    }

    public Map<String, Integer> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Map<String, Integer> occurrences) {
        this.occurrences = occurrences;
    }

    public Integer getDistinct() {
        return distinct;
    }

    public void setDistinct(Integer distinct) {
        this.distinct = distinct;
    }

    public Map<String, AnalysisResult> getHistory() {
        return history;
    }

    public void setHistory(Map<String, AnalysisResult> history) {
        this.history = history;
    }
}