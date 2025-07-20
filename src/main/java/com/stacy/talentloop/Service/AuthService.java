package com.stacy.talentloop.Service;

import com.stacy.talentloop.Requests.AuthRequest;
import com.stacy.talentloop.Requests.RegisterRequest;
import com.stacy.talentloop.Response.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    AuthResponse register(RegisterRequest request);
}
