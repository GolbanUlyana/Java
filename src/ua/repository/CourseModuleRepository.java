package ua.repository;

import ua.model.CourseModule;
import java.util.Comparator;
import java.util.List;

public class CourseModuleRepository extends GenericRepository<CourseModule> {
    
    public CourseModuleRepository() {
        super(new CourseModuleIdentityExtractor(), "CourseModuleRepository");
    }
    
    public List<CourseModule> sortByDurationAsc() {
        Comparator<CourseModule> durationComparator = Comparator.comparingInt(CourseModule::durationHours);
        return sortByComparator(durationComparator, "тривалістю (зростання)");
    }
    
    public List<CourseModule> sortByDurationDesc() {
        Comparator<CourseModule> durationComparator = Comparator.comparingInt(CourseModule::durationHours).reversed();
        return sortByComparator(durationComparator, "тривалістю (спадання)");
    }
    
    public List<CourseModule> sortByNameLength() {
        Comparator<CourseModule> lengthComparator = Comparator.comparingInt(m -> m.name().length());
        return sortByComparator(lengthComparator, "довжиною назви");
    }
    
    public List<CourseModule> sortByHasVideo() {
        Comparator<CourseModule> videoComparator = (m1, m2) -> {
            boolean m1HasVideo = m1.hasVideoContent();
            boolean m2HasVideo = m2.hasVideoContent();
            return Boolean.compare(m2HasVideo, m1HasVideo);
        };
        return sortByComparator(videoComparator, "наявністю відео");
    }
    
    public List<CourseModule> sortByContentLength() {
        Comparator<CourseModule> contentComparator = Comparator.comparingInt(m -> m.content().length());
        return sortByComparator(contentComparator, "довжиною контенту");
    }
}