package personal.febry.bcpraetorian.data;

//Kind of a bad implementation, but this is for OOP, cz 1 class for OOP is suckz
//Sumpah ini maksa banget :'( FirebaseUser udh punya semua datanya

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {

    private final String name, UID, email;

    public UserData(String name, String UID, String email){
        this.name = name;
        this.UID = UID;
        this.email = email;
    }

    protected UserData(Parcel in) {
        name = in.readString();
        UID = in.readString();
        email = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };


    public String getName() {
        return name;
    }

    public String getUID() {
        return UID;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(UID);
        parcel.writeString(email);
    }
}
