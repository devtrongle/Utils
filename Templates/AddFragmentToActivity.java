    /**
     * Thêm fragment vào activity
     * @param fragments danh sách fragment cần khởi tạo (Fragment cần hiển thị sẽ ở vị trí đầu)
     * @param fragment fragment cần show
     */
    private void addFragment(Fragment[] fragments, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragments != null){
            //Thêm tất cả fragment vào activity và ẩn đi
            for(Fragment f : fragments){
                transaction.add(mView.frameContainer.getId(), f, f.getClass().getSimpleName());
                transaction.addToBackStack(f.getClass().getSimpleName());
                transaction.hide(f);
            }
            //Hiển thị fragment vị trí 0
            if(fragments.length > 0){
                transaction.show(fragments[0]);
            }
        }else{
            Fragment fm = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
            //Ẩn toàn bộ fragment
            for(Fragment f : getSupportFragmentManager().getFragments()){
                if(!f.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())){
                    transaction.hide(f);
                }
            }

            if(fm != null && fm.isAdded()){
                if(!fm.isVisible()){
                    transaction.show(fm);
                }
            }else{
                transaction.add(mView.frameContainer.getId(), fragment, fragment.getClass().getSimpleName());
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
        }
        transaction.commit();
    }
