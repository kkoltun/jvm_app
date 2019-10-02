package sda.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Data repository created only for training purposes.
 * This implementation purposefully causes memory leak.
 * This should not be implemented this way in real application.
 */
public class DataRepository {

    private List<DataModel> dataModelList = new ArrayList<>();

    private DataRepository() {}

    static DataRepository getInstance() {
        return DataRepository.RepositoryHolder.INSTANCE;
    }

    private static class RepositoryHolder {
        private static final DataRepository INSTANCE = new DataRepository();
    }

    List<DataModel> getAll() {
        return dataModelList;
    }

    Optional<DataModel> get(int id) {
        return dataModelList.stream()
                .filter(e -> e.getId() == id)
                .findAny();
    }

    void save(DataModel e) {
        dataModelList.add(e);
    }

    void deleteAll() {
        dataModelList.clear();
    }

    void delete(int id) {
        dataModelList = dataModelList.stream()
                .filter(e -> e.getId() != id)
                .collect(Collectors.toList());
    }
}
