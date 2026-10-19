public class Score {
    int id;
    String name;
    String course;
    double score;

    public Score() {
    }

    public Score(int id, String name, String course, double score) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.score = score;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", score=" + score +
                '}';
    }
}
