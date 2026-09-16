package za.ac.cput.service;

import za.ac.cput.domain.Order;

import java.util.List;

public interface IService<C, S> {

    C create(C c);

    Order read(S id);

    C update(C c);

    boolean delete(S id);

    List<C> getAll();
}
