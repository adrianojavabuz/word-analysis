package com.prover.wordanalysis.dao;

import com.prover.wordanalysis.AnalysisResult;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateless
public class AnalysisResultDAO {

    private static final Logger logger = Logger.getLogger(AnalysisResultDAO.class.getName());

    @PersistenceContext(unitName = "wordAnalysisPU")
    private EntityManager em;

    public void persist(AnalysisResult result) {
        if (em == null) {
            logger.warning("EntityManager não injetado no DAO!");
            return;
        }
        try {
            em.persist(result);
            logger.info("Persistido no banco, ID: " + result.getId());
        } catch (Exception e) {
            logger.severe("Erro ao persistir resultado: " + e.getMessage());
            throw e;
        }
    }

    public Map<String, AnalysisResult> findAll() {
        if (em == null) {
            logger.warning("EntityManager não injetado no DAO!");
            return new HashMap<>();
        }
        try {
            Query query = em.createQuery("SELECT a FROM AnalysisResult a");
            List<AnalysisResult> results = query.getResultList();
            Map<String, AnalysisResult> history = new HashMap<>();
            for (AnalysisResult result : results) {
                history.put(String.valueOf(result.getId()), result);
                logger.info("Carregado do banco: ID=" + result.getId() + ", Phrase=" + result.getPhrase());
            }
            logger.info("Histórico carregado do banco: " + history);
            return history;
        } catch (Exception e) {
            logger.severe("Erro ao carregar histórico: " + e.getMessage());
            return new HashMap<>();
        }
    }
}