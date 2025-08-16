package com.prover.wordanalysis;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Entity
@Table(name = "analysis_results")
public class AnalysisResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String phrase;

    @Column
    private int distinctWords;

    @Lob
    @Column
    private String occurrencesJson;


    public AnalysisResult() {}

    /**
     * Construtor com parâmetros para inicializar a entidade.
     * @param phrase A frase analisada.
     * @param distinctWords A quantidade de palavras distintas na frase.
     * @param occurrencesJson O mapa de ocorrências serializado como JSON.
     */
    public AnalysisResult(String phrase, int distinctWords, String occurrencesJson) {
        this.phrase = phrase;
        this.distinctWords = distinctWords;
        this.occurrencesJson = occurrencesJson;
    }

    /**
     * Método para parsear o JSON de ocorrências de volta para um Map<String, Integer>.
     * Retorna um mapa vazio se o JSON for null, vazio ou inválido.
     * @return Um mapa contendo as palavras e suas respectivas contagens.
     */
    public Map<String, Integer> getOccurrencesMap() {
        if (occurrencesJson == null || occurrencesJson.trim().isEmpty()) {
            return new HashMap<>();
        }
        try {
            return new Gson().fromJson(occurrencesJson, new TypeToken<Map<String, Integer>>(){}.getType());
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPhrase() { return phrase; }
    public void setPhrase(String phrase) { this.phrase = phrase; }
    public int getDistinctWords() { return distinctWords; }
    public void setDistinctWords(int distinctWords) { this.distinctWords = distinctWords; }
    public String getOccurrencesJson() { return occurrencesJson; }
    public void setOccurrencesJson(String occurrencesJson) { this.occurrencesJson = occurrencesJson; }
}