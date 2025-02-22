package com.amcamp.cineAI.domain.movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovieConstants {
    public static final List<String> MOVIE_LIST =
            Arrays.asList(
                    // 드라마
                    "포레스트 검프",
                    "기생충",
                    "버닝",

                    // 액션
                    "어벤져스: 엔드게임",
                    "올드보이",
                    "베를린",

                    // 로맨스
                    "노트북",
                    "지금, 만나러 갑니다",
                    "내 머리 속의 지우개",

                    // 코미디
                    "슈퍼배드",
                    "과속스캔들",
                    "극한직업",

                    // 스릴러
                    "세븐",
                    "신세계",
                    "살인의 추억",

                    // 호러
                    "더 바이탈",
                    "곡성",
                    "여고괴담: 여우계단",

                    // SF
                    "인셉션",
                    "승리호",

                    // 애니메이션
                    "토이 스토리 3",
                    "마당을 나온 암탉",

                    // 전쟁
                    "라이언 일병 구하기",

                    // 뮤지컬
                    "라라랜드");

    public static List<String> getRandomMovies(int size) {
        List<String> randomMovies = new ArrayList<>();
        Random random = new Random();

        // 리스트 크기보다 작은 값을 입력하면, 그만큼만 랜덤으로 선택
        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(MOVIE_LIST.size()); // 0부터 MOVIE_LIST 크기 - 1까지의 랜덤 인덱스
            randomMovies.add(MOVIE_LIST.get(randomIndex));
        }

        return randomMovies;
    }
}
