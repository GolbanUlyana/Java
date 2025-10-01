package ua.repository;

import ua.util.Logger;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GenericRepository<T> {
    private final List<T> items;
    private final IdentityExtractor<T> identityExtractor;
    private final String repositoryName;
    
    public GenericRepository(IdentityExtractor<T> identityExtractor, String repositoryName) {
        this.items = new CopyOnWriteArrayList<>();
        this.identityExtractor = identityExtractor;
        this.repositoryName = repositoryName;
        Logger.info("Створено репозиторій: " + repositoryName);
    }
    
    // Додавання елемента
    public boolean add(T item) {
        if (item == null) {
            Logger.warning("Спроба додати null об'єкт до репозиторію " + repositoryName);
            return false;
        }
        
        String identity = identityExtractor.extractIdentity(item);
        if (findByIdentity(identity).isPresent()) {
            Logger.warning("Об'єкт з identity '" + identity + "' вже існує в репозиторії " + repositoryName);
            return false;
        }
        
        items.add(item);
        Logger.info("Додано об'єкт до репозиторію " + repositoryName + ": " + identity);
        return true;
    }
    
    // Додавання всіх елементів
    public boolean addAll(Collection<T> itemsToAdd) {
        if (itemsToAdd == null || itemsToAdd.isEmpty()) {
            Logger.warning("Спроба додати порожню колекцію до репозиторію " + repositoryName);
            return false;
        }
        
        int addedCount = 0;
        for (T item : itemsToAdd) {
            if (add(item)) {
                addedCount++;
            }
        }
        
        Logger.info("Додано " + addedCount + " з " + itemsToAdd.size() + 
                   " об'єктів до репозиторію " + repositoryName);
        return addedCount > 0;
    }
    
    // Видалення елемента за identity
    public boolean removeByIdentity(String identity) {
        Optional<T> itemToRemove = findByIdentity(identity);
        if (itemToRemove.isPresent()) {
            items.remove(itemToRemove.get());
            Logger.info("Видалено об'єкт з репозиторію " + repositoryName + ": " + identity);
            return true;
        }
        
        Logger.warning("Об'єкт з identity '" + identity + "' не знайдено для видалення в " + repositoryName);
        return false;
    }
    
    // Видалення елемента
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        return removeByIdentity(identityExtractor.extractIdentity(item));
    }
    
    // Пошук за identity
    public Optional<T> findByIdentity(String identity) {
        return items.stream()
                   .filter(item -> identityExtractor.extractIdentity(item).equals(identity))
                   .findFirst();
    }
    
    // Отримання всіх елементів
    public List<T> getAll() {
        Logger.debug("Отримано всі об'єкти з репозиторію " + repositoryName + ": " + items.size() + " елементів");
        return new ArrayList<>(items);
    }
    
    // Отримання кількості елементів
    public int size() {
        return items.size();
    }
    
    // Перевірка на порожність
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    // Очищення репозиторію
    public void clear() {
        int size = items.size();
        items.clear();
        Logger.info("Очищено репозиторій " + repositoryName + ". Видалено " + size + " елементів");
    }
    
    // Пошук за предикатом
    public List<T> findByPredicate(java.util.function.Predicate<T> predicate) {
        List<T> result = items.stream()
                             .filter(predicate)
                             .toList();
        Logger.debug("Знайдено " + result.size() + " об'єктів за предикатом в " + repositoryName);
        return result;
    }
    
    @Override
    public String toString() {
        return String.format("GenericRepository{name='%s', size=%d}", repositoryName, items.size());
    }
}