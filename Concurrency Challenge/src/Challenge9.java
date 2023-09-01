public class Challenge9 {

    //CHALLENGE 9: SPOT AND FIX THE PROBLEM

    public static void main(String[] args) {
        NewTutor tutor = new NewTutor();
        NewStudent student = new NewStudent(tutor);
        tutor.setStudent(student);

        Thread tutorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                tutor.studyTime();
            }
        });

        Thread studentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                student.handInAssignment();
            }
        });

        tutorThread.start();
        studentThread.start();
    }
}

class NewTutor {
    private NewStudent student;

    public void setStudent(NewStudent student) {
        this.student = student;
    }

    public void studyTime() {

        synchronized (this) {
            System.out.println("Tutor has arrived");
            synchronized (student) {
                try {
                    // wait for student to arrive and hand in assignment
                    this.wait();
                } catch (InterruptedException e) {

                }
                student.startStudy();
                System.out.println("Tutor is studying with student");
            }
        }
    }

    public void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class NewStudent {
    private NewTutor tutor;

    NewStudent(NewTutor tutor) {
        this.tutor = tutor;
    }

    public synchronized void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public void handInAssignment() {
        synchronized (tutor) {
            tutor.getProgressReport();
           // synchronized (this) {
                //by removing this syncronised block, I'm releasing the student locks before it calls wait()
                System.out.println("Student handed in assignment");
                tutor.notifyAll();
           // }
        }
    }
}
