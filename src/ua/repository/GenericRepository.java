package ua.repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import ua.util.Logger;

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
    
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        return removeByIdentity(identityExtractor.extractIdentity(item));
    }
    
    public Optional<T> findByIdentity(String identity) {
        return items.stream()
                   .filter(item -> identityExtractor.extractIdentity(item).equals(identity))
                   .findFirst();
    }
    
    public List<T> getAll() {
        Logger.debug("Отримано всі об'єкти з репозиторію " + repositoryName + ": " + items.size() + " елементів");
        return new ArrayList<>(items);
    }
    
    public int size() {
        return items.size();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public void clear() {
        int size = items.size();
        items.clear();
        Logger.info("Очищено репозиторій " + repositoryName + ". Видалено " + size + " елементів");
    }
    
    public List<T> findByPredicate(java.util.function.Predicate<T> predicate) {
        List<T> result = items.stream()
                             .filter(predicate)
                             .toList();
        Logger.debug("Знайдено " + result.size() + " об'єктів за предикатом в " + repositoryName);
        return result;
    }
    
    // НОВІ МЕТОДИ ДЛЯ СОРТУВАННЯ
    
    public List<T> sortByIdentity(String order) {
        Logger.info("Сортування репозиторію " + repositoryName + " за порядком: " + order);
        
        List<T> sortedList = new ArrayList<>(items);
        
        switch (order.toLowerCase()) {
            case "asc", "ascending" -> {
                sortedList.sort(Comparator.comparing(item -> identityExtractor.extractIdentity(item)));
                Logger.debug("Відсортовано за зростанням identity");
            }
            case "desc", "descending" -> {
                sortedList.sort(Comparator.comparing((T item) -> identityExtractor.extractIdentity(item)).reversed());
                Logger.debug("Відсортовано за спаданням identity");
            }
            default -> {
                Logger.warning("Невідомий порядок сортування: " + order + ". Повернено несортований список.");
                return sortedList;
            }
        }
        
        return sortedList;
    }
    
    public List<T> sortByComparator(Comparator<T> comparator, String sortDescription) {
        Logger.info("Сортування репозиторію " + repositoryName + " за: " + sortDescription);
        
        List<T> sortedList = new ArrayList<>(items);
        sortedList.sort(comparator);
        
        Logger.debug("Відсортовано " + sortedList.size() + " елементів за: " + sortDescription);
        return sortedList;
    }
    
    public List<T> sortNatural() {
        Logger.info("Сортування репозиторію " + repositoryName + " за натуральним порядком");
        
        List<T> sortedList = new ArrayList<>(items);
        
        if (!sortedList.isEmpty() && sortedList.get(0) instanceof Comparable) {
            sortedList.sort(null);
            Logger.debug("Відсортовано за натуральним порядком (Comparable)");
        } else {
            Logger.warning("Тип не підтримує Comparable. Повернено несортований список.");
        }
        
        return sortedList;
    }
    
    @Override
    public String toString() {
        return String.format("GenericRepository{name='%s', size=%d}", repositoryName, items.size());
    }
}