package domain;

import java.util.Comparator;

public class MediaCategoryComparator implements Comparator<MediaCategory> {
    public MediaCategoryComparator() {
    }

    @Override
    public  int compare(MediaCategory o1, MediaCategory o2){
        if(o1.getPosition() < o2.getPosition()){
            return -1;
        }
        else if (o1.getPosition() > o2.getPosition()){
              return 1;
        }
        else
            return 0;
    }
}

