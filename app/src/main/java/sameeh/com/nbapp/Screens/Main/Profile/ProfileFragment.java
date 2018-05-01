package sameeh.com.nbapp.Screens.Main.Profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.Base.BaseFragment;
import sameeh.com.nbapp.Base.LoggedInScreen;
import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.ApiError;
import sameeh.com.nbapp.Models.User;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.Services.ProfilePictureService;
import sameeh.com.nbapp.api.LoggedIn.LoggedInApiManager;

public class ProfileFragment extends BaseFragment implements LoggedInScreen {

    private ProfileFragmentListener listener;

    private LoggedInApiManager loggedInApi;
    private User user;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.name)
    TextView nameTextView;

    @BindView(R.id.email)
    TextView emailTextView;

    @BindView(R.id.profile_picture)
    ImageView profilePictureImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }
    public void checkEnoughPermissions(){
        Permissions.check(getActivity(), new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                "Camera and storage permissions are required because...", new Permissions.Options()
                        .setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Info"),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        EasyImage.openChooserWithGallery(ProfileFragment.this, "PICK PICTURE: ", 0);

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                onPhotosReturned(imagesFiles);
            }
        });
    }

    private void onPhotosReturned(List<File> imagesFiles) {
        File imageFile = imagesFiles.get(0);
        Intent intent = new Intent(getActivity(), ProfilePictureService.class);
        intent.putExtra(ProfilePictureService.IMAGE_EXTRA_KEY,imageFile);
        getActivity().startService(intent);
        Picasso.get().load(imageFile).into(profilePictureImageView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        localStorageManager = LocalStorageManager.getInstance(getActivity());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        loggedInApi = LoggedInApiManager.getInstance(getActivity());

        loggedInApi.getProfile().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    showUserDetails(user);
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        ApiError apiError = parseApiErrorString(errorJson);
                        actBasedOnApiErrorCode(apiError);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                fetchUserFromLocalStorage();
            }
        });
    }

    private void showUserDetails(User user) {
        if (!TextUtils.isEmpty(user.getName())) {
            nameTextView.setText(user.getName());
        }
        if (!TextUtils.isEmpty(user.getEmail())) {
            emailTextView.setText(user.getEmail());
        }
    }

    private void fetchUserFromLocalStorage() {
        User user = localStorageManager.getUser();
        if (user != null) {
            showUserDetails(user);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ProfileFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void notLoggedInAnymore() {
        localStorageManager.deleteUser();
        listener.onProfileFetchFailure();
    }

    @OnClick(R.id.profile_picture)
    public void attemptChangeProfilePicture() {
        checkEnoughPermissions();

    }

    public interface ProfileFragmentListener {
        void onProfileFetchFailure();
    }

}
