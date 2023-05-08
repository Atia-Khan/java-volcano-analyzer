import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.function.Function;
// import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
// import java.util.Arrays;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
    private List<Volcano> volcanos;

    public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
        try{
            String path = pathOpt.orElse("volcano.json");
            URL url = this.getClass().getClassLoader().getResource(path);
            String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            volcanos = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, Volcano.class));
        } catch(Exception e){
            throw(e);
        }
    }

    public Integer numbVolcanoes(){
        return volcanos.size();
    }

    //add methods here to meet the requirements in README.md

      public List<Volcano> eruptedInEighties() {
        return volcanos.stream()
            .filter(v -> v.getYear() >= 1980 && v.getYear() <= 1989)
            .collect(Collectors.toList());
    }

    //Second user Story

    public String[] highVEI(){
        return volcanos.stream()
        .filter(v -> v.getVEI() >= 6)
        .map(Volcano::getName)
        .toArray(String[]::new);
    }

    //Third user Story

    public Volcano mostDeadly(){
        return volcanos.stream()
        .max(Comparator.comparingInt(v -> Integer.parseInt(v.getDEATHS())))
        .orElse(null);
    }

    //Fourth user Story
  public double causedTsunami() {
// long getTsunamiPercentage = volcanos.stream()
//     .filter(v -> !v.getTsu().isEmpty())
//     .count();

// return (double) getTsunamiPercentage / volcanos.size() * 100;

double x = volcanos.stream().filter(v->v.getTsu().equals("tsu")).count();
    return x/volcanos.size() * 100;
  }


  //5th user story


  public String mostCommonType() {
    return volcanos.stream()
            .map(Volcano::getType)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
}

//6th test case


public int eruptionsByCountry(String country) {
    return (int) volcanos.stream()
    .filter(v -> country.equals(v.getCountry()))
    .count();
    }


    //7th test case

    public double averageElevation() {
        return volcanos.stream()
                .mapToDouble(Volcano::getElevation)
                .average()
                .orElse(0);
    }


    // 8th test case
    public String[] volcanoTypes() {
        return volcanos.stream()
                .map(Volcano::getType)
                .distinct()
                .toArray(String[]::new);
    }
    // 9th test case

    public double percentNorth() {
      double value =  volcanos.stream().filter(v-> v.getLatitude()> 0 ).count();
       return  value/ volcanos.size()*100;
    }


      // 10th test case
    public String[] manyFilters() {
        return  volcanos.stream().filter( v-> v.getYear()>1800 ).
        filter(v-> v.getTsu().equals("")).
        filter(v-> v.getLatitude()<0).
        filter(v-> v.getVEI() == 5 ).map(Volcano :: getName).toArray(String []:: new);
        
    }
  // 11th test case
    public String[] elevatedVolcanoes(int i) {
        return  volcanos.stream().filter(v-> v.getElevation() > i).map(Volcano :: getName).toArray(String[]:: new);
    }

    //12th test case

    public String[] topAgentsOfDeath() {
        return volcanos.stream()
        .sorted((i,j) -> Integer.parseInt(j.getDEATHS()) - Integer.parseInt(i.getDEATHS()))
        .limit(10).filter(v -> !v.getAgent().isEmpty())
        .map(v -> Arrays.asList(v.getAgent().split(","))).flatMap(List::stream)
        .distinct()
        .collect(Collectors.toList())
        .toArray(new String[0]);
    }


    
    
}
