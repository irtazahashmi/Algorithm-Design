package Greedy.OptimalCaching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvictionSchedule {

    // Outputs updated cache after each memory address character using furthest in the future algorithm
    public static List<List<Character>> evictionSchedule(Character[] memoryAddress, Character[] cache) {
        int cacheHits = 0;
        int cacheMisses = 0;
        List<List<Character>> cacheUpdates = new ArrayList<>();
        List<Character> evictedSchedule = new ArrayList<>();

        // go through the memory address
        for(int i = 0; i < memoryAddress.length; i++) {
            // if cache hit we good
            if (cacheHit(cache, memoryAddress[i])) cacheHits++;
            else {
                // if cache miss, update the cache using Furthest in future algo
                Character evictedChar = update(memoryAddress, cache, memoryAddress[i], i);
                evictedSchedule.add(evictedChar);
                cacheMisses++;
            }

            // the cache at each iteration (not necessary)!
            List<Character> cacheAtThisPoint = new ArrayList<>();
            for(char c : cache) cacheAtThisPoint.add(c);
            cacheUpdates.add(cacheAtThisPoint);
        }

        System.out.println("Hit Count: " + cacheHits);
        System.out.println("Miss Count: " + cacheMisses);
        System.out.println(cacheUpdates);
        System.out.println(evictedSchedule);
        return cacheUpdates;
    }

    // update chache
    private static Character update(Character[] memoryAddress, Character[] cache, Character character, int startIndex) {
        int[] cacheCharCount = new int[cache.length];
        Arrays.fill(cacheCharCount, 0);

        // Find the index where each character occurs first from cache
        for(int i = startIndex; i < memoryAddress.length; i++) {
            for(int j = 0; j < cache.length; j++) {
                if (cache[j] == memoryAddress[i] && cacheCharCount[j] == 0) cacheCharCount[j] = i;
            }
        }

        // If couldn't find the index for a cache char, that char value will be evicted.
        for(int i = 0; i < cache.length; i++){
            if (cacheCharCount[i] == 0) {
                Character evicted = cache[i];
                cache[i] = character;
                return evicted;
            }
        }

        // If none of the cache are in future memory address, remove the one that is needed furthest in future.
        int fifIndex = Integer.MIN_VALUE;
        for(int i : cacheCharCount) fifIndex = Math.max(fifIndex, i);

        for(int i = 0; i < cache.length; i++) {
            if (fifIndex == cacheCharCount[i]) {
                Character evicted = cache[i];
                cache[i] = character;
                return evicted;
            }
        }

        return null;
    }

    // Checks if there has been a cache hit
    private static boolean cacheHit(Character[] cache, Character character) {
        for(char c: cache) {
            if (c == character) return true;
        }
        return false;
    }

    public static void main (String[]args){
        //Test 1. Hit: 8 Miss: 8 Schedule = [c, d, b, c, f, a, f, b]
//        Character[] memoryAddress = new Character[]{'a', 'a', 'd', 'e', 'b', 'b', 'a', 'c', 'f', 'd', 'e', 'a', 'f', 'b', 'e', 'c'};
//        Character[] cache = new Character[] {'a', 'b', 'c'};
//        evictionSchedule(memoryAddress, cache);
//
//        System.out.println();
//
//        // Test 2. Hit: 5 Miss: 2 Schedule = [a, c]
//        Character[] m = new Character[] {'a','b','c','b','c','a','b'};
//        Character[] c= new Character[] {'a','b'};
//        evictionSchedule(m, c);


        Character[] m = new Character[] {'a','e','a','c','b','a','c','e','d','e','d','b','a'};
        Character[] c= new Character[] {'0','0','0'};
        evictionSchedule(m, c);
    }
}
