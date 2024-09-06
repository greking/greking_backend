package com.greking.Greking.Recommendation.service;

public class CosineSimilarity {

    // 코사인 유사도 계산 함수
    public static double calculate(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i]; // 내적
            normA += Math.pow(vectorA[i], 2);      // 벡터 A의 크기
            normB += Math.pow(vectorB[i], 2);      // 벡터 B의 크기
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}

