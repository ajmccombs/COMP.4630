package com.aokellermann.nutritionapp.descriptions;

import com.aokellermann.nutritionapp.utils.Trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DescriptionsImpl implements Descriptions {
    private final Map<Integer, String> idDescriptionsMap = new HashMap<>();
    private final Trie<Integer> keywords = new Trie<>();

    @Override
    public void add(Integer id, String description) {
        idDescriptionsMap.put(id, scrubDescription(description));
        keywords.addAll(parseKeywords(description.toLowerCase()), id);
    }

    @Override
    public String getDescription(Integer id) {
        return idDescriptionsMap.get(id);
    }

    @Override
    public Collection<Integer> searchKeywords(String query) {
        if (query.length() <= 2) return new LinkedList<>();

        // Get keywords

        Collection<String> keywordStrings = parseKeywords(query.toLowerCase());
        if (keywordStrings.size() == 0) return new LinkedList<>();

        // Get items that match keywords

        ArrayList<List<Set<Integer>>> orderedKeywordMatchesList = new ArrayList<>();
        orderedKeywordMatchesList.ensureCapacity(keywordStrings.size());
        for (String keyword : keywordStrings) {
            orderedKeywordMatchesList.add(keywords.searchPrefix(keyword));
        }

        // Get set of items that share all keywords

        // TODO: For some reason, this is really slow on first call...
        //  it should be investigated further...
        Set<Integer> sharedIDs = new HashSet<>();
        Set<Integer> keywordIDs = new HashSet<>();

        Iterator<List<Set<Integer>>> it = orderedKeywordMatchesList.iterator();
        it.next().forEach(sharedIDs::addAll);
        while (it.hasNext()) {
            keywordIDs.clear();
            it.next().forEach(keywordIDs::addAll);
            sharedIDs.retainAll(keywordIDs);  // set intersection
        }

        // Get priority

        ArrayList<Map<Integer, Integer>> prior = new ArrayList<>();
        prior.ensureCapacity(keywordStrings.size());

        for (List<Set<Integer>> orderedKeywordMatches : orderedKeywordMatchesList) {
            Map<Integer, Integer> priorMap = new HashMap<>();
            prior.add(priorMap);

            int priority = orderedKeywordMatches.size() - 1;
            for (Set<Integer> ids : orderedKeywordMatches) {
                for (Integer id : ids) {
                    if (sharedIDs.contains(id)) {
                        priorMap.merge(id, priority, Integer::sum);
                    }
                }
                --priority;
            }
        }

        // Order priorities

        Map<Integer, Set<Integer>> orderedPriority = new TreeMap<>(Collections.reverseOrder());
        for (Map<Integer, Integer> keywordPriorEntry : prior) {
            for (Map.Entry<Integer, Integer> priorEntry : keywordPriorEntry.entrySet()) {
                orderedPriority.putIfAbsent(priorEntry.getValue(), new HashSet<>());
                orderedPriority.get(priorEntry.getValue()).add(priorEntry.getKey());
            }
        }

        // Collect ids

        List<Integer> orderedPriorityList = new LinkedList<>();
        orderedPriority.values().forEach(orderedPriorityList::addAll);
        return orderedPriorityList;
    }

    protected Collection<String> parseKeywords(String str) {
        return scrubKeywords(str.split(" "));
    }

    protected Collection<String> scrubKeywords(String[] keywords) {
        List<String> newKeywords = new LinkedList<>();
        for (String keyword : keywords) {
            String newKeyword = scrubKeyword(keyword);
            if (newKeyword != null)
                newKeywords.add(newKeyword);
        }
        return newKeywords;
    }

    protected String scrubKeyword(String keyword) {
        if (keyword.length() <= 1) return null;
        keyword = keyword.trim();
        if (keyword.charAt(keyword.length() - 1) == ',')
            keyword = keyword.substring(0, keyword.length() - 1);
        if (keyword.length() <= 1) return null;
        return keyword;
    }

    protected String scrubDescription(String description) {
        List<String> list = new LinkedList<>();
        for (String str : description.split(" ")) {
            // A lot of branded food in FDC data is all caps, so this just makes it display nicer
            if (str.length() >= 2 && Character.isUpperCase(str.charAt(0)) && Character.isUpperCase(str.charAt(1))) {
                list.add(str.charAt(0) + str.substring(1).toLowerCase());
            } else {
                list.add(str);
            }
        }
        return String.join(" ", list);
    }
}
