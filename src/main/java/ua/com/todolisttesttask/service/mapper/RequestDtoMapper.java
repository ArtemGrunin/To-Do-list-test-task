package ua.com.todolisttesttask.service.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);

    default T mapToModel(D dto, Long userId) {
        return mapToModel(dto);
    }
}
