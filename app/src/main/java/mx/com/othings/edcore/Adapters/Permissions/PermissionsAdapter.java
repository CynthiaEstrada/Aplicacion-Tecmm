package mx.com.othings.edcore.Adapters.Permissions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.List;

import mx.com.othings.edcore.Fragments.permissions.CameraPermission;
import mx.com.othings.edcore.Fragments.permissions.LocationPermission;
import mx.com.othings.edcore.Fragments.permissions.ReadAndWriteMemory;

public class PermissionsAdapter extends AbstractFragmentStepAdapter {

    private List<Fragment> fragmentList;

    public PermissionsAdapter(@NonNull FragmentManager fm, @NonNull Context context, List<Fragment> fragmentList) {
        super(fm, context);
        this.fragmentList = fragmentList;

    }

    @Override
    public Step createStep(int position) {

        return (Step) fragmentList.get(position);

    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
