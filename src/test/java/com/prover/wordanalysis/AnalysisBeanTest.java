package com.prover.wordanalysis;

import com.prover.wordanalysis.dao.AnalysisResultDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AnalysisBeanTest {

    @InjectMocks
    private AnalysisBean bean;

    @Mock
    private AnalysisResultDAO analysisResultDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(bean.analysisResultDAO);
    }


    @Test
    public void testLoadHistory() {
        // Arrange
        AnalysisResult result = new AnalysisResult("teste anterior", 1, "{}");
        result.setId(1L);
        Map<String, AnalysisResult> mockHistory = new HashMap<>();
        mockHistory.put("1", result);
        when(analysisResultDAO.findAll()).thenReturn(mockHistory);

        // Act
        bean.init();

        // Assert
        assertEquals(1, bean.getHistory().size());
        assertEquals("teste anterior", bean.getHistory().get("1").getPhrase());
        verify(analysisResultDAO).findAll();
    }

    @Test(expected = RuntimeException.class)
    public void testAnalyzeWithDAOFailure() {
        // Arrange
        String phrase = "teste";
        bean.setPhrase(phrase);
        doThrow(new RuntimeException("Erro simulado")).when(analysisResultDAO).persist(any(AnalysisResult.class));
        when(analysisResultDAO.findAll()).thenReturn(new HashMap<>());

        // Act
        bean.analyze();

        // Assert
        verify(analysisResultDAO).persist(any(AnalysisResult.class));
        verify(analysisResultDAO).findAll();
    }
}