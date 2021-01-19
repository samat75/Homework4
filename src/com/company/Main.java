package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1800;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {270, 260, 250, 250, 300, 240, 280, 150};
    public static int[] heroesDamages = {20, 25, 15, 0, 5, 15, 20, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundCounter = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundCounter++;
        changeBossDefence();
        bossAngryState();
        bossHits();
        heroesHit();
        medicTreats();
        printStatistics();
    }

    public static void medicTreats() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] < 100) {
                Random r = new Random();
                int random = r.nextInt(50);
                heroesHealth[i] = heroesHealth[i] + random;
                System.out.println("Medic healed [" + heroesAttackType[i] + "] by [" + random + "]");
                break;
            }
        }
    }

    public static void bossAngryState() {
        if (bossHealth <= 200) {
            Random r = new Random();
            int healthRand = r.nextInt(31) + 20;
            int damageRand = r.nextInt(11) + 10;
            bossHealth = bossDamage + healthRand;
            bossDamage = bossDamage + damageRand;
            System.out.println("Boss became angry: " + "increased health by " + healthRand + " and damage " + damageRand);
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamages.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesAttackType[i] == bossDefenceType) {
                    Random r = new Random();
                    int coeff = r.nextInt(7) + 2; // 2,3,4,5,6,7,8
                    if (bossHealth - heroesDamages[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamages[i] * coeff;
                    }
                    System.out.println(heroesAttackType[i] + " critical damage " + heroesDamages[i] * coeff);
                } else {
                    if (bossHealth - heroesDamages[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamages[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    //Thor
                    Random rand = new Random();
                    int stun = rand.nextInt(2);
                    if (heroesHealth[7] > 0 && stun == 1) {
                        System.out.println("Boss stunned until next round!");
                        break;
                    }
                    //Lucky
                    if (heroesHealth[5] > 0 && stun != 1) {
                        Random rl = new Random();
                        int randomForLucky = rl.nextInt(2);
                        if (randomForLucky == 1) {
                            System.out.println("Lucky was lucky");
                            continue;
                        }
                    }
                    //Berserk
                    if (heroesHealth[6] > 0 && stun != 1) {
                        Random rb = new Random();
                        int randomForBerserk = rb.nextInt(15) + 15;
                        heroesHealth[6] = heroesHealth[6] + randomForBerserk;
                        bossHealth = bossHealth - randomForBerserk;
                        System.out.println("Berserk blocked [" + randomForBerserk + "] damage");
                    }
                    //Golem
                    if (heroesHealth[4] > 0) {
                        heroesHealth[i] = heroesHealth[i] - (bossDamage - (bossDamage / 5));
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND ----- " + roundCounter);
        System.out.println("______________");
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]);
        }
        System.out.println("______________");
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }

        return allHeroesDead;
    }
}
