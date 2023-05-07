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
// import java.util.Arrays;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
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
long getTsunamiPercentage = volcanos.stream()
    .filter(v -> !v.getTsu().isEmpty())
    .count();

return (double) getTsunamiPercentage / volcanos.size() * 100;
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

}
