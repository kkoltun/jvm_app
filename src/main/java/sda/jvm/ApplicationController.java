package sda.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
class ApplicationController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private DataRepository repo;
  private TextAnalyzer textAnalyzer;

  public ApplicationController() {
    this.repo = DataRepository.getInstance();
    this.textAnalyzer = new TextAnalyzer();
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/list")
  @ResponseBody
  HttpEntity<List<DataModel>> getAll() {
    return ResponseEntity.ok(repo.getAll());
  }

  @GetMapping("/list/{id}")
  @ResponseBody
  HttpEntity<DataModel> get(@PathVariable int id) {
    return repo.get(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/generate")
  @ResponseBody
  HttpEntity<String> generate(@RequestParam int size) {
    int actualSize = repo.getAll().size();
    IntStream.range(0, size)
        .mapToObj(e -> new DataModel(Integer.valueOf(e + actualSize), "x"))
        .forEach(e -> repo.save(e));

    return ResponseEntity.ok("Generated " + size + " DataModel objects");
  }

  @GetMapping("/delete_all")
  @ResponseBody
  HttpEntity<String> deleteAll() {
    int size = repo.getAll().size();
    repo.deleteAll();
    return ResponseEntity.ok("Deleted all DataModel objects: " + size);
  }

  @GetMapping("/size")
  @ResponseBody
  HttpEntity<String> size() {
    return ResponseEntity.ok("Actual size: " + repo.getAll().size());
  }

  @PostMapping("/analyze")
  @ResponseBody
  AnalysisResults analyzeText(@RequestParam("file") MultipartFile file) throws IOException {
    long startTime = System.currentTimeMillis();
    AnalysisResults analysisResults = textAnalyzer.analyze(file.getInputStream());
    analysisResults.sortAndLimit(10);
    long endTime = System.currentTimeMillis();

    log.info("File {} analyzed. Time elapsed: {} ms.", file.getOriginalFilename(), endTime - startTime);

    return analysisResults;
  }
}
