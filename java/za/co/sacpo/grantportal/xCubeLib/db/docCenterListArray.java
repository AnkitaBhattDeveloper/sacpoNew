package za.co.sacpo.grantportal.xCubeLib.db;

public class docCenterListArray {

    String  id,
            student_id,
            grant_id,
            previous_document,
            previous_document_btn,
            name_of_document,
            d_c_doc_status,
            d_c_doc_type,
            upload_document_type,
            download_document_link,
            download_document_btn,
            upload_document_link;

    public docCenterListArray(String id, String student_id, String grant_id, String previous_document, String previous_document_btn, String name_of_document, String d_c_doc_status, String d_c_doc_type, String upload_document_type, String download_document_link, String download_document_btn, String upload_document_link) {
        this.id = id;
        this.student_id = student_id;
        this.grant_id = grant_id;
        this.previous_document = previous_document;
        this.previous_document_btn = previous_document_btn;
        this.name_of_document = name_of_document;
        this.d_c_doc_status = d_c_doc_status;
        this.d_c_doc_type = d_c_doc_type;
        this.upload_document_type = upload_document_type;
        this.download_document_link = download_document_link;
        this.download_document_btn = download_document_btn;
        this.upload_document_link = upload_document_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getGrant_id() {
        return grant_id;
    }

    public void setGrant_id(String grant_id) {
        this.grant_id = grant_id;
    }

    public String getPrevious_document() {
        return previous_document;
    }

    public void setPrevious_document(String previous_document) {
        this.previous_document = previous_document;
    }

    public String getPrevious_document_btn() {
        return previous_document_btn;
    }

    public void setPrevious_document_btn(String previous_document_btn) {
        this.previous_document_btn = previous_document_btn;
    }

    public String getName_of_document() {
        return name_of_document;
    }

    public void setName_of_document(String name_of_document) {
        this.name_of_document = name_of_document;
    }

    public String getD_c_doc_status() {
        return d_c_doc_status;
    }

    public void setD_c_doc_status(String d_c_doc_status) {
        this.d_c_doc_status = d_c_doc_status;
    }

    public String getD_c_doc_type() {
        return d_c_doc_type;
    }

    public void setD_c_doc_type(String d_c_doc_type) {
        this.d_c_doc_type = d_c_doc_type;
    }

    public String getUpload_document_type() {
        return upload_document_type;
    }

    public void setUpload_document_type(String upload_document_type) {
        this.upload_document_type = upload_document_type;
    }

    public String getDownload_document_link() {
        return download_document_link;
    }

    public void setDownload_document_link(String download_document_link) {
        this.download_document_link = download_document_link;
    }

    public String getDownload_document_btn() {
        return download_document_btn;
    }

    public void setDownload_document_btn(String download_document_btn) {
        this.download_document_btn = download_document_btn;
    }

    public String getUpload_document_link() {
        return upload_document_link;
    }

    public void setUpload_document_link(String upload_document_link) {
        this.upload_document_link = upload_document_link;
    }

    @Override
    public String toString() {
        return "docCenterListArray{" +
                "id='" + id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", grant_id='" + grant_id + '\'' +
                ", previous_document='" + previous_document + '\'' +
                ", previous_document_btn='" + previous_document_btn + '\'' +
                ", name_of_document='" + name_of_document + '\'' +
                ", d_c_doc_status='" + d_c_doc_status + '\'' +
                ", d_c_doc_type='" + d_c_doc_type + '\'' +
                ", upload_document_type='" + upload_document_type + '\'' +
                ", download_document_link='" + download_document_link + '\'' +
                ", download_document_btn='" + download_document_btn + '\'' +
                ", upload_document_link='" + upload_document_link + '\'' +
                '}';
    }
}
