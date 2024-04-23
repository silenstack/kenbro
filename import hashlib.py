import hashlib
import secrets

# Password Security Rule
def hash_password(password):
    # Use a secure hashing algorithm (e.g., SHA-256) with a random salt
    salt = secrets.token_hex(16)
    hashed_password = hashlib.sha256((password + salt).encode()).hexdigest()
    return hashed_password, salt

def is_password_valid(hashed_password, salt, entered_password):
    # Verify the entered password against the stored hash and salt
    return hashed_password == hashlib.sha256((entered_password + salt).encode()).hexdigest()

# Access Control Rule
class Resource:
    def __init__(self, name):
        self.name = name
        self.authorized_personnel = set()

    def grant_access(self, user):
        self.authorized_personnel.add(user)

    def revoke_access(self, user):
        if user in self.authorized_personnel:
            self.authorized_personnel.remove(user)

    def check_access(self, user):
        return user in self.authorized_personnel

