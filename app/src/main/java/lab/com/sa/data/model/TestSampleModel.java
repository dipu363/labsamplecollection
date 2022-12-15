package lab.com.sa.data.model;

public class TestSampleModel {


    int sample_id;
    int lab_id;
    String pat_id;
    String pat_name;
    String pat_phone;
    String pat_address;
    String test_name;
    String test_result;
    String test_status;

    public TestSampleModel() {
    }

    public TestSampleModel(int lab_id, String pat_id, String pat_name, String pat_phone, String pat_address, String test_name, String test_status) {
        this.lab_id = lab_id;
        this.pat_id = pat_id;
        this.pat_name = pat_name;
        this.pat_phone = pat_phone;
        this.pat_address = pat_address;
        this.test_name = test_name;
        this.test_status = test_status;
    }

    public int getSample_id() {
        return sample_id;
    }

    public void setSample_id(int sample_id) {
        this.sample_id = sample_id;
    }

    public int getLab_id() {
        return lab_id;
    }

    public void setLab_id(int lab_id) {
        this.lab_id = lab_id;
    }

    public String getPat_id() {
        return pat_id;
    }

    public void setPat_id(String pat_id) {
        this.pat_id = pat_id;
    }

    public String getPat_name() {
        return pat_name;
    }

    public void setPat_name(String pat_name) {
        this.pat_name = pat_name;
    }

    public String getPat_phone() {
        return pat_phone;
    }

    public void setPat_phone(String pat_phone) {
        this.pat_phone = pat_phone;
    }

    public String getPat_address() {
        return pat_address;
    }

    public void setPat_address(String pat_address) {
        this.pat_address = pat_address;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_result() {
        return test_result;
    }

    public void setTest_result(String test_result) {
        this.test_result = test_result;
    }

    public String getTest_status() {
        return test_status;
    }

    public void setTest_status(String test_status) {
        this.test_status = test_status;
    }
}
