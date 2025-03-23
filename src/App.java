
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {  
    public static void main(String[] args) throws Exception {
        String[] options={"Add task","View all tasks","View Pending tasks","Update task","Delete task","Mark as completed","Exit"};
        App app=new App();
        ArrayList<String> toDoList =new ArrayList<String>();
        ArrayList<String> completedList =new ArrayList<String>();
        Scanner scr=new Scanner(System.in);
        boolean flag=true;
        System.out.println("Welcome to ToDoList App\n");
        app.importData(toDoList, completedList);
        while(flag){ 
            for(int i=0;i<options.length;i++){
                System.out.println((i+1)+" : "+options[i]);
            }
            System.out.println("Select the option");
            int option=scr.nextInt();
            switch (option) {
                case 1:
                    app.addTask(toDoList);
                    break;
                case 2:
                    app.viewAllTasks(toDoList,completedList);
                    break;
                case 3:
                    app.viewPendingTasks(toDoList);
                    break;
                case 4:
                    app.updateTask(toDoList);
                    break;
                case 5:
                    app.deleteTask(toDoList);
                    break;
                case 6:
                    app.completedTask(toDoList, completedList);
                    break;
                case 7:
                    flag=false;
                    break;
            }
            if(flag!=true){
                app.export(toDoList, completedList);
                System.out.println("Bye");
                break;
            }
                
        }
        

    }

    public void addTask(ArrayList<String> toDoList){
        Scanner scr=new Scanner(System.in);
        System.out.println("Enter the task");
        // String trash=scr.nextLine();
        String task=scr.nextLine();
        toDoList.add(task);
        System.out.println("Task :\""+task+"\" is added in ToDoList\n");
    }

    public void viewAllTasks(ArrayList<String> toDoList,ArrayList<String> completedList){
        if(toDoList.size()==0 && completedList.size()==0){
            System.out.println("No tasks available\n");
        }
        else if (toDoList.size()==0 && completedList.size()>0){
            System.out.println("No pending tasks\n");
            for(int i=0;i<completedList.size();i++){
                System.out.println((i+1)+" : "+completedList.get(i)+" -> Completed");
            }
            System.out.println("\n");
        }
        else if(toDoList.size()>0 && completedList.size()==0){
            for(int i=0;i<toDoList.size();i++){
                System.out.println((i+1)+" : "+toDoList.get(i)+" -> Pending");
            }
            System.out.println("\n");
        }
        else{
            for(int i=0;i<toDoList.size();i++){
                System.out.println((i+1)+" : "+toDoList.get(i)+" -> Pending");
            }
            System.out.println("\n");
            for(int i=0;i<completedList.size();i++){
                System.out.println((i+1)+" : "+completedList.get(i)+" -> Completed");
            }
            System.out.println("\n");
        }
    }

    public void viewPendingTasks(ArrayList<String> toDoList){
        if(toDoList.size()==0){
            System.out.println("No pending tasks\n");
        }
        else{
            for(int i=0;i<toDoList.size();i++){
                System.out.println((i+1)+" : "+toDoList.get(i)+" -> Pending");
            }
            System.out.println("\n");
        }
    }

    public void updateTask(ArrayList<String> toDoList){
        Scanner scr=new Scanner(System.in);
        App app=new App();
        app.viewPendingTasks(toDoList);
        if(toDoList.size()>0){
            System.out.println("Enter the task id to update");
            int task_id=scr.nextInt();
            if(task_id<=toDoList.size()){
                System.out.println("Enter the updated task");
                String trash=scr.nextLine();
                String task=scr.nextLine();
                toDoList.set(task_id-1, task);
                System.out.println("Task updated successfully");
            }
            else{
                System.out.println("No task found with given task id");
            }
            
        }
        
    }

    public void deleteTask(ArrayList<String> toDoList){
        Scanner scr=new Scanner(System.in);
        App app=new App();
        app.viewPendingTasks(toDoList);
        if(toDoList.size()>0){
            System.out.println("Enter the task id to delete");
            int task_id=scr.nextInt();
            if(task_id<=toDoList.size()){
                toDoList.remove(task_id-1);
                System.out.println("Task deleted successfully");
            }
            else{
                System.out.println("No task found with given task id");
            }
            
        }
    }

    public void completedTask(ArrayList<String> toDoList,ArrayList<String> completedList){
        Scanner scr=new Scanner(System.in);
        App app=new App();
        app.viewPendingTasks(toDoList);
        if(toDoList.size()>0){
            System.out.println("Enter the task id to mark as complete");
            int task_id=scr.nextInt();
            if(task_id<=toDoList.size()){
                String task=toDoList.get(task_id-1).toString();
                completedList.add(task);
                toDoList.remove(task_id-1);
                System.out.println("Task marked as completed successfully");
            }
            else{
                System.out.println("No task found with given task id");
            }
        }
    }

    public void export(ArrayList<String> toDoList,ArrayList<String> completedList){
        System.out.println("Exporting the data");
        File file=new File("../files/Tasks.csv");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("Pending Tasks\n");
            for(int i=0;i<toDoList.size();i++){
                writer.write(toDoList.get(i)+"\n");
            }
            writer.write("\n");
            writer.write("Completed Tasks\n");
            for(int i=0;i<completedList.size();i++){
                writer.write(completedList.get(i)+"\n");
            }
            writer.close();
        } 
        catch (Exception e) {
            System.err.println("Error occured while exporting the data");
            e.printStackTrace();
        }
        System.out.println("Exported successfully");
    }

    public void importData(ArrayList<String> toDoList,ArrayList<String> completedList){
        System.out.println("Importing the data");
        File file=new File("../files/Tasks.csv");
        try {
            Scanner reader=new Scanner(file);
            while(reader.hasNextLine()){
                String data=reader.nextLine();
                if(data.equals("Pending Tasks")){
                    while(reader.hasNextLine()){
                        String task=reader.nextLine();
                        if(task.equals(""))
                            break;
                        toDoList.add(task);
                    }
                }
                if(data.equals("Completed Tasks")){
                    while(reader.hasNextLine()){
                        String task=reader.nextLine();
                        if(task.equals(""))
                            break;
                        completedList.add(task);
                    }
                }
            }
            System.out.println("Imported successfully");
            reader.close();
        } 
        catch (Exception e) {
            System.err.println("Error occured while importing the data");
            e.printStackTrace();
        }
    }

}
