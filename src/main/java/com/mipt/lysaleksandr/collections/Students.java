package com.mipt.lysaleksandr.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Students {
  public ArrayList findStudentsByGradeRange(Map<Integer, Student> map, double minGrade, double maxGrade) {
    ArrayList<Student> ans = new ArrayList<>();
    for (Student student : map.values()) {
      if (minGrade <= student.getGrade() && student.getGrade() <= maxGrade) {
        ans.add(student);
      }
    }
    return ans;
  }

  public ArrayList getTopNStudents(TreeMap<Integer, Student> map, int n) {
    ArrayList<Student> ans = new ArrayList<>();
    int check = map.size() - n;
    for (Student student : map.values()) {
      if (check == 0) {
        ans.add(student);
      } else {
        check -= 1;
      }
    }
    return ans;
  }

  public static void main(String[] args) {
    Map<Integer, Student> hashMap = new HashMap<>();
    Map<Integer, Student> treeMap = new TreeMap<>();
  }
}
