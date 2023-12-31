package za.co.sacpo.grantportal.xCubeLib.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import za.co.sacpo.grantportal.ContactSupportPublicA;
import za.co.sacpo.grantportal.xCubeMentor.MChangePassword;
import za.co.sacpo.grantportal.xCubeMentor.MEditProfileA;
import za.co.sacpo.grantportal.xCubeMentor.MUploadPicA;
import za.co.sacpo.grantportal.xCubeMentor.attendance.MAttMonthlyA;
import za.co.sacpo.grantportal.xCubeMentor.attendance.MCurrentAttA;
import za.co.sacpo.grantportal.xCubeMentor.claims.MApproveA;
import za.co.sacpo.grantportal.xCubeMentor.claims.MClaimAttApproveA;
import za.co.sacpo.grantportal.xCubeMentor.claims.MLearnerGrantDetailsA;
import za.co.sacpo.grantportal.xCubeMentor.claims.MMonthlyFeedbackA;
import za.co.sacpo.grantportal.xCubeMentor.feedback.MDeleteFeedbackA;
import za.co.sacpo.grantportal.xCubeMentor.feedback.MEditFeedbackA;
import za.co.sacpo.grantportal.xCubeMentor.feedback.MFeedbackA;
import za.co.sacpo.grantportal.xCubeMentor.leaves.MLeaveApproval;
import za.co.sacpo.grantportal.xCubeMentor.messages.MChatA;
import za.co.sacpo.grantportal.xCubeMentor.notes.MAddNote;
import za.co.sacpo.grantportal.xCubeMentor.notes.MDeleteNoteA;
import za.co.sacpo.grantportal.xCubeMentor.notes.MEditNotelistA;
import za.co.sacpo.grantportal.xCubeMentor.queries.MContactSupportPrivateA;
import za.co.sacpo.grantportal.xCubeMentor.queries.MQueriesCommentsA;
import za.co.sacpo.grantportal.xCubeMentor.student.MChangeOffDay;
import za.co.sacpo.grantportal.xCubeMentor.training.MAddTrainingPlanA;
import za.co.sacpo.grantportal.xCubeMentor.training.MEditTrainingPlanA;
import za.co.sacpo.grantportal.xCubeMentor.training.MTrainingPlanA;
import za.co.sacpo.grantportal.xCubeMentor.upload.MUploadClaimFormA;
import za.co.sacpo.grantportal.xCubeMentor.workX.MAddWorksA;
import za.co.sacpo.grantportal.xCubeMentor.workX.MChangeSWorkXA;
import za.co.sacpo.grantportal.xCubeMentor.workX.MEditWorkXA;
import za.co.sacpo.grantportal.xCubeStudent.SChangePasswordA;
import za.co.sacpo.grantportal.xCubeStudent.editprofile.SEDitProfileStepThreeA;
import za.co.sacpo.grantportal.xCubeStudent.editprofile.SEditProfileStepFourA;
import za.co.sacpo.grantportal.xCubeStudent.editprofile.SEditProfileStepOneA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.EditAttCalenderA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.SAttPostCommentA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.SignInA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.SignOutA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.UploadAttendanceA;
import za.co.sacpo.grantportal.xCubeStudent.bank.ProofOfBankingA;
import za.co.sacpo.grantportal.xCubeStudent.bank.SEditBankDetailA;
import za.co.sacpo.grantportal.xCubeStudent.claims.SImpactStudiesA;
import za.co.sacpo.grantportal.xCubeStudent.claims.SCMonthlyFeedbackA;
import za.co.sacpo.grantportal.xCubeStudent.claims.SConfirmBankDetailsA;
import za.co.sacpo.grantportal.xCubeStudent.claims.SSubmitClaim;
import za.co.sacpo.grantportal.xCubeStudent.editprofile.SEditProfileStepTwoA;
import za.co.sacpo.grantportal.xCubeStudent.editprofile.SEditProfileStepTwoBA;
import za.co.sacpo.grantportal.xCubeStudent.feedback.SAddFeedbackA;
import za.co.sacpo.grantportal.xCubeStudent.feedback.SEditFeedbackA;
import za.co.sacpo.grantportal.xCubeStudent.feedback.SRemoveFeedbackA;
import za.co.sacpo.grantportal.xCubeStudent.forms.SUploadFormA;
import za.co.sacpo.grantportal.xCubeStudent.leaves.SEditLeaveA;
import za.co.sacpo.grantportal.xCubeStudent.leaves.SLConfirmationA;
import za.co.sacpo.grantportal.xCubeStudent.leaves.SLeavesDA;
import za.co.sacpo.grantportal.xCubeStudent.messages.SChatA;
import za.co.sacpo.grantportal.xCubeStudent.queries.SContactSupportPrivateA;
import za.co.sacpo.grantportal.xCubeStudent.queries.SQueriesCommentsA;
import za.co.sacpo.grantportal.xCubeStudent.upload.DownloadCenterA;
import za.co.sacpo.grantportal.xCubeStudent.upload.SUploadDocumentA;
import za.co.sacpo.grantportal.xCubeStudent.upload.UploadCv;
import za.co.sacpo.grantportal.xCubeStudent.upload.UploadMultipleDocsA;

public class ErrorDialog {
    public static boolean isErrorDialogShowing = false;
    public static androidx.appcompat.app.AlertDialog ErrorDialog;
    private static final String TAG = ErrorDialog.class.getSimpleName();
    public static void printLogs(String funcs,String msg){
        //Log.i("OSG-"+TAG+"__"+funcs,msg);
    }
    public static void removeErrorDialog() {
        if ((ErrorDialog != null) && (ErrorDialog.isShowing())) {
            ErrorDialog.dismiss();
            isErrorDialogShowing = false;
            ErrorDialog = null;
        }
    }
    public static void showErrorDialog(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton) {
        isErrorDialogShowing = true;
        printLogs("showErrorDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                //activityClass.finish();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialog(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();

                //activityClass.finish();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMChangeOffDay(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MChangeOffDay activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSChangePasswordA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SChangePasswordA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }





    public static void showSuccessDialogMEditWorkXA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MEditWorkXA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogMChangePassword(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MChangePassword activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    } public static void showSuccessDialogMUploadPicA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MUploadPicA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogAssignTrainingPlan(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MTrainingPlanA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogAddTrainingPlan(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MAddTrainingPlanA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogUploadClaimFormM(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MUploadClaimFormA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogUploadDocumentS(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SUploadDocumentA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
 public static void showSuccessDialogUploadMultipleDocS(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final UploadMultipleDocsA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogEditTrainingPlan(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MEditTrainingPlanA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogSCEditBankDetails(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SImpactStudiesA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSEditBankDetailA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditBankDetailA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMLearnerGrantDetailsA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MLearnerGrantDetailsA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogDownlodCenterA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final DownloadCenterA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogRemoveLeaveA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SLeavesDA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
     public static void showSuccessDialogAddNote(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MAddNote activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogViewAttLA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MAttMonthlyA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMAttSummaryList(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MClaimAttApproveA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSCMonthlyFeedbackA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SCMonthlyFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMMonthlyFeedbackA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MMonthlyFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogEditAttendance(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MCurrentAttA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSignOutA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SignOutA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirectorr();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSignOutAA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SignOutA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirectorr();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogMChangeSWorkXA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MChangeSWorkXA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    } public static void showSuccessDialogSignInA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SignInA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSignInAA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SignInA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccesUploadClaim(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SUploadFormA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSQueriesCommentsA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SQueriesCommentsA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }



    public static void showSuccessDialogMQueriesCommentsA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MQueriesCommentsA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMAddWorksA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MAddWorksA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogContactSupportPublicA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final ContactSupportPublicA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogsubmitClaim(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SSubmitClaim activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSConfirmBankDetailsA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SConfirmBankDetailsA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }



    public static void showSuccessSContactSupport(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SContactSupportPrivateA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSUploadChatImg(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SChatA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogMUploadChatImg(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MChatA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogMDeleteNote(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MDeleteNoteA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMEditNote(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MEditNotelistA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogEditFeedback(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogMDeleteFeedback(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MDeleteFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogMFeedback(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSAddFeedback(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SAddFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogMApproveA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MApproveA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogRemoveFeedback(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SRemoveFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogMEditFeedback(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MEditFeedbackA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }



    public static void showSuccessDialogMLeaveApproval(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MLeaveApproval activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }



    public static void showSuccessDialogMEditProfile(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MEditProfileA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogMContactSupport(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final MContactSupportPrivateA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogSContactSupportPrivateA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SContactSupportPrivateA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogSEditProfile(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditProfileStepOneA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSEditProfileThree(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEDitProfileStepThreeA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSEditProfileFour(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditProfileStepFourA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
public static void showSuccessDialogSEditProfileTwo(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditProfileStepTwoA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSEditProfileTwoB(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditProfileStepTwoBA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }
    public static void showSuccessDialogSAttCommentsA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SAttPostCommentA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogApplyLeave(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SLConfirmationA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogEditLeave(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SEditLeaveA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }



   /* public static void showSuccessDialogSSignout(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final SignOutA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        android.support.v7.app.AlertDialog.Builder localBuilder = new android.support.v7.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }*/


    public static void showSuccessDialogEditAttCalenderA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final EditAttCalenderA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogUploadAttendanceA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final UploadAttendanceA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


    public static void showSuccessDialogUploadCv(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final UploadCv activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }

    public static void showSuccessDialogProofOfBankingA(final Context context, final AppCompatActivity activityClass, String title, String message, String closeButton, final ProofOfBankingA activityToRedirect) {
        isErrorDialogShowing = true;
        printLogs("showSuccessDialog","isErrorDialogShowing : "+isErrorDialogShowing);
        androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(activityClass);
        localBuilder.setCancelable(false);
        localBuilder.setTitle(title).setMessage(message).setNegativeButton(closeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                removeErrorDialog();
                activityToRedirect.customRedirector();
            }
        });
        ErrorDialog = localBuilder.create();
        ErrorDialog.show();
    }


}