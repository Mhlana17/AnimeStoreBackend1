package za.ac.cput.service;

import java.util.List;
import java.util.Optional;

public interface IService<C, S> {

    C create(C c);

    Optional<C> read(S id);

    C update(C c);

    boolean delete(S id);

    List<C> getAll();
}
