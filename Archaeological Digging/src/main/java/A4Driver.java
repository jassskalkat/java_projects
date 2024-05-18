import java.util.*;
import edu.princeton.cs.algs4.*;

/**
 * Driver program reading from stdin and writing to stdout
 * solves the archeology assignment 4.
 *
 * To run this use gradle run, or click the run button beside
 * any of the tests in the A4DriverTest class
 *
 */
public class A4Driver {

    public static void main(String[] args)
    {
        int count;
        int bonesCount = 0;
        int maxDepth = 0;
        Digraph bonesGraph;
        Map<String,Integer> bones = new TreeMap<>();
        ArrayList<String> bonesOrder = new ArrayList<>();
        ArrayList<Bones> bone = new ArrayList<>();

        //create a scanner and get the first number in the file.
        Scanner scan = new Scanner(System.in);
        count = scan.nextInt();
        scan.nextLine();
        bonesGraph = new Digraph(count);

        //loop through each line and read it
        while(scan.hasNextLine())
        {
            String line = scan.nextLine();

            if(line.contains("rests"))
            {
                bonesOrder.add(line);
            }
            else
            {
                bones.put(line, bonesCount++);
                bone.add(new Bones(line));
            }
        }

        for (String s : bonesOrder)
        {
            String firstBone = s.substring(0, s.indexOf(" "));
            String secondBone = s.substring(s.indexOf(" on ") + 4);

            bonesGraph.addEdge(bones.get(firstBone), bones.get(secondBone));

            int firstBoneIndex = -1;
            int secondBoneIndex = -1;
            for(int i = 0; i < bone.size(); i++)
            {
                if(bone.get(i).boneName.equals(firstBone))
                    firstBoneIndex = i;

                if(bone.get(i).boneName.equals(secondBone))
                    secondBoneIndex = i;
            }

            if(firstBoneIndex != -1 && secondBoneIndex != -1)
                bone.get(firstBoneIndex).restsOn(bone.get(secondBoneIndex));

            if(bone.get(secondBoneIndex).depth > maxDepth)
                maxDepth = bone.get(secondBoneIndex).depth;

        }

        DirectedCycle cycle = new DirectedCycle(bonesGraph);

        if(bonesCount == 1)
        {
            String[] array = bones.keySet().toArray(new String[0]);
            System.out.println(array[0]);
        }
        else if(cycle.hasCycle())
        {
            System.out.println("impossible");
        }
        else
        {
            //check that all bones have the correct depth, and if the max changed increment it appropriately
            for (Bones item : bone) {
                item.checkDepth();
                if (item.depth > maxDepth)
                    maxDepth = item.depth;
            }

            //this time check from the end of the list to the beginning to make sure everything is correct.
            for(int i = bone.size() - 1; i >= 0; i--)
            {
                bone.get(i).checkDepth();
                if(bone.get(i).depth > maxDepth)
                    maxDepth = bone.get(i).depth;
            }

            //go through the list one depth at a time and print all the bones that are at that level. (bones at the same depth can be printed in any order)
            for(int i = 1; i <= maxDepth; i++)
            {
                for (Bones value : bone) {
                    if (value.depth == i)
                        System.out.println(value.boneName);
                }
            }
        }
    }

    /**
     * a class to add the bones into one structure and check and get their correct location under the ground.
     * (I decided not to implement a method to check for a cycle here, I used one of the given classes for that).
     */
    private static class Bones
    {
        //this represents the placement of the bone from 1 to n, when n represents how many bones are on top of it.
        private int depth = 1;

        String boneName;

        //the bones on top and below of the current one
        ArrayList<Bones> ON = new ArrayList<>();
        ArrayList<Bones> below = new ArrayList<>();

        /**
         * a constructor to create a Bones object
         * @param name the name of the bone
         */
        public Bones(String name)
        {
            boneName = name;
        }

        /**
         * a method to link two bones together in a specific order.
         * @param boneB the bone that will be below the current one.
         */
        public void restsOn(Bones boneB)
        {
            //add the given bone to be below the current one, and the current one to be on top of the given one.
            below.add(boneB);
            boneB.ON.add(this);
        }

        public void checkDepth()
        {
            //record the current depth of the bone
            int mxDepth = depth;

            //check the depth of each bone on top of it, and get the maximum depth + 1 to be the new depth for the current bone
            for(Bones bone: ON)
            {
                if(bone.depth >= mxDepth)
                    mxDepth = bone.depth + 1;
            }
            depth = mxDepth;
        }
    }
}