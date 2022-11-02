package za.co.sacpo.grant.xCubeLib.spinners;

/**
 * Created by vin on 29/8/16.
 */
public class SpinnerSet {

    private int _id;
    private String _name;
    private String _code;

    public SpinnerSet(){
        this._id = 0;
        this._name = "";

    }

    public void setId(int id){
        this._id = id;
    }

    public int getId(){
        return this._id;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getName(){
        return this._name;
    }
}