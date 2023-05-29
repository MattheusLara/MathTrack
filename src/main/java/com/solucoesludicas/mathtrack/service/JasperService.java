package com.solucoesludicas.mathtrack.service;

public interface JasperService {
    void addParams(String key, Object value);
    byte[] exportarPDF(String nomeDoRelatorio);
}
